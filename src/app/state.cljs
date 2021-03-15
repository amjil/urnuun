(ns app.state
  (:require
   [rum.core :as rum]
   [app.util :as util]))

(def ^:private state
  (atom
   {:ime/active? true
    :ime/candidate []
    :ime/candidate-page 0
    :ime/input ""}))

(defn sub
  [ks]
  (if (coll? ks)
    (util/react (rum/cursor-in state ks))
    (util/react (rum/cursor state ks))))

(defn set-state!
  [path value]
  (if (vector? path)
    (swap! state assoc-in path value)
    (swap! state assoc path value)))

(defn update-state!
  [path f]
  (if (vector? path)
    (swap! state update-in path f)
    (swap! state update path f)))