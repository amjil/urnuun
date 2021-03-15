(ns app.components.keyboard.candidate
  (:require
   [rum.core :as rum]
   [app.state :as state]))

(rum/defc view < rum/reactive []
  (let [cands (state/sub :ime/candidate)]
    (if-not (empty? cands)
      [:div.fixed.z-10.overflow-x-auto.mode-lr
       [:div.m-4
        [:ol.list-decimal
         (for [x cands]
           [:li {:ref (:id x)} (:char_word x)])]]])))