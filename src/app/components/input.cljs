(ns app.components.input
  (:require
   [rum.core :as rum]))

(rum/defc input < rum/reactive
  [state value opts]
  [:div 
   (merge opts {:contenteditable "true"})
   value])
