(ns app.handler.keyboard.api
  (:require
   [app.util :as util]
   [clojure.string :as str]
   [cljs-bean.core :refer [bean ->clj ->js]]
   [app.state :as state]))

(defonce base-url "http://localhost:3002/api")

(defonce xvlvn-url (str base-url "/candidate"))

(defonce xvlvn-update (str base-url "/update-order"))

(defonce url-next-words (str base-url "/next-words"))

(defn candidate [cand res-func]
  (js/console.log "candidate string " (clj->js cand))
  (let [url (str xvlvn-url "?input=" cand)]
    (util/fetch url #(-> (sort-by :active_order > (into #{} %)) res-func) #(js/alert "api error!"))))

;; (defn update-order [id candstr]
;;   (go (let [response (<! (http/get xvlvn-update {:query-params {:input candstr :id id} :with-credentials? false}))]
;;         (js/console.log "Update order !!!!")
;;         (js/console.log (clj->js (:body response))))))

(defn next-words [candstr id res-func]
  (js/console.log "next-words ------")
  (let [url (str url-next-words "?input=" candstr "&id=" id)]
    (util/fetch url #(-> (sort-by :active_order > (into #{} %)) res-func) #(js/alert "api error!"))))

(comment
  (js/console.log "hello")
  (candidate "eh" #(js/console.log (clj-js %)))

  util/fetch
  (util/fetch (str xvlvn-url "?input=eh") js/console.log js/console.log)
  (util/fetch (str url-next-words "?input=ej&id=3034" ) js/console.log js/console.log)
  (candidate "nmr" #(state/set-state! :ime/candidate %))
  (state/set-state! :ime/input "ab")
  
  (next-words "eh" 2439 js/console.log))


