(ns app.components.main
  (:require [rum.core :as rum]
            ["mousetrap" :as mousetrap]
            [goog.object :as gobj]))

(def periodic-update-mixin
  { :did-mount    (fn [state]
                    (let [comp      (:rum/react-component state)
                          callback #(rum/request-render comp)
                          interval  (js/setInterval callback 1000)]
                       (assoc state ::interval interval)))
    :will-unmount (fn [state]
                    (js/clearInterval (::interval state))
                    (dissoc state ::interval)) })

(rum/defc timer < periodic-update-mixin []
  [:div (.toISOString (js/Date.))])

(defn prevent-default-behavior
  [f]
  (fn [state e]
    (f state e)
    ;; return false to prevent default browser behavior
    ;; and stop event from bubbling
    false))

(def bind! (gobj/get mousetrap "bind"))

;; (bind! "q" #(js/console.log "aaaaa"))
(defn trigger [key]
  (bind! key #(js/console.log "pressed", key)))

(comment

  (require '[rum.core :as rum])
  (require '[goog.object :as gobj])

  (def bind! (gobj/get mousetrap "bind"))
  (bind! "q" #(js/console.log "aaaaa"))

  (trigger "a")
  (trigger "b")

  (trigger "mod+b")

  ;
  )
