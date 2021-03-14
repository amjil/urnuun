(ns app.util
  (:require
   [cljs-bean.core :as bean]
   [goog.dom :as gdom]
   [goog.object :as gobj]
   [goog.string :as gstring]
   [goog.string.format]
   [goog.userAgent]
   [clojure.string :as string]
   [clojure.pprint :refer [pprint]]
   [clojure.walk :as walk]
   [promesa.core :as p]))

(defn fetch
  ([url on-ok on-failed]
   (fetch url {} on-ok on-failed))
  ([url opts on-ok on-failed]
   (-> (js/fetch url (bean/->js opts))
       (.then (fn [resp]
                (if (>= (.-status resp) 400)
                  (on-failed resp)
                  (if (.-ok resp)
                    (-> (.json resp)
                        (.then bean/->clj)
                        (.then #(on-ok %)))
                    (on-failed resp))))))))

(defn upload
  [url file on-ok on-failed on-progress]
  (let [xhr (js/XMLHttpRequest.)]
    (.open xhr "put" url)
    (gobj/set xhr "onload" on-ok)
    (gobj/set xhr "onerror" on-failed)
    (when (and (gobj/get xhr "upload")
               on-progress)
      (gobj/set (gobj/get xhr "upload")
                "onprogress"
                on-progress))
    (.send xhr file)))

(defn post
  [url body on-ok on-failed]
  (fetch url {:method "post"
              :headers {:Content-Type "application/json"}
              :body (js/JSON.stringify (clj->js body))}
         on-ok
         on-failed))

(defn patch
  [url body on-ok on-failed]
  (fetch url {:method "patch"
              :headers {:Content-Type "application/json"}
              :body (js/JSON.stringify (clj->js body))}
         on-ok
         on-failed))

(defn delete
  [url on-ok on-failed]
  (fetch url {:method "delete"
              :headers {:Content-Type "application/json"}}
         on-ok
         on-failed))
