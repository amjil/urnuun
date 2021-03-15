(ns app.components.keyboard.candidate
  (:require
   [rum.core :as rum]
   [app.state :as state]))

(rum/defc view < rum/reactive []
  (let [cands (state/sub :ime/candidate)
        cand-page (state/sub :ime/candidate-page)
        total-record (count cands)
        total (- (int (/ total-record 9)) (if (zero? (mod total-record 9)) 1 0))]
    (if-not (empty? cands)
      [:div.pt-5.z-10.overflow-x-auto.mode-lr.leading-4.bg-gray-50.rounded-xl.shadow-2xl.w-44.h-32.absolute
       [:div.mx-2.my-2
        [:ol.list-decimal
         (for [x (take 9 (drop (* 9 cand-page) cands))]
           [:li {:ref (:id x)} (:char_word x)])]
        [:div.flex.flex-cel.justify-between
         [:p {:class (if (= 0 cand-page) "" "text-gray-500")} "<"]
         [:p {:class (if (> total cand-page) "" "text-gray-500")} ">"]]]])))
