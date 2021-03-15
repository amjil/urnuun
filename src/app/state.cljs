(ns app.state
  (:require
   [rum.core :as rum]))

(defonce ^:private state
  (atom
   {:ime/candidate []
    :ime/candidate-page 0}))

(defn sub
  [ks]
  (if (coll? ks)
    (rum/react (rum/cursor-in state ks))
    (rum/react (rum/cursor state ks))))

(defn set-state!
  [path value]
  (if (vector? path)
    (swap! state assoc-in path value)
    (swap! state assoc path value)))
