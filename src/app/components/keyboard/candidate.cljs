(ns app.components.keyboard.candidate
  (:require
   [rum.core :as rum]
   [app.state :as state]))

(rum/defc view < rum/reactive []
  (let [cands (state/sub :ime/candidate)
        cand-page (state/sub :ime/candidate-page)]
    (if-not (empty? cands)
      [:div.pt-5.z-10.overflow-x-auto.mode-lr.leading-4.bg-white.rounded-xl.shadow-md.w-44.h-32.absolute.inset-0
       [:div.mx-2.my-2
        [:ol.list-decimal
         (for [x (take 9 (drop (* 9 cand-page) cands))]
           [:li {:ref (:id x)} (:char_word x)])]
        (if (< 9 (count cands))
          [:p ">>"])]])))
