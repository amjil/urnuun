(ns app.keyboard.api
  (:require 
   [app.util :as util]
   [clojure.string :as str]
   [cljs-bean.core :refer [bean ->clj ->js]]
   [app.state :as state]))

(defonce base-url "http://localhost:3002/api")

(defonce xvlvn-url (str base-url "/candidate"))

(defonce xvlvn-update (str base-url "/update-order"))

(defonce url-next-words (str base-url "/next-words"))

(defn candidate [cands res-func]
  (js/console.log "candidate string " (clj->js cands))
  (let [input (str/join "," cands)
        url (str xvlvn-url "?input=" input)]
    (util/fetch url #(-> (sort-by :active_order > (into #{} %)) res-func) #(js/alert "api error!"))))

;; (defn update-order [id candstr]
;;   (go (let [response (<! (http/get xvlvn-update {:query-params {:input candstr :id id} :with-credentials? false}))]
;;         (js/console.log "Update order !!!!")
;;         (js/console.log (clj->js (:body response))))))

;; (defn next-words [candstr id res-func]
;;   (let [response (<! (http/get url-next-words {:query-params {:input candstr :id id} :with-credentials? false}))]
;;         (js/console.log "next words status " (:status response))
;;         (js/console.log (clj->js (map #(:char_word %) (:body response))))
;;         (res-func (sort-by :id > (into #{} (:body response))))))

(comment
  (js/console.log "hello")
  (candidate ["ab"] js/console.log)
  util/fetch
  (util/fetch (str xvlvn-url "?input=abej") js/console.log js/console.log)
  (candidate ["b"] #(state/set-state! :ime/candidate %)))
  

