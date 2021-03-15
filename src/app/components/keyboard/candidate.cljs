(ns app.components.keyboard.candidate
  (:require
   [rum.core :as rum]
   [app.state :as state]))

(rum/defc view < rum/reactive []
  (let [cands (state/sub :ime/candidate)
        cand-page (state/sub :ime/candidate-page)
        total-record (count cands)
        total (- (int (/ total-record 9)) (if (zero? (mod total-record 9)) 1 0))
        input-str (state/sub :ime/input)]
    (if-not (empty? input-str)
      [:div.z-10.overflow-x-auto.border-purple-100.border.bg-gray-50.rounded-xl.shadow-xl.absolute.justify-between.flex.flex-col.divide-y
       [:p.pl-3 input-str]
       [:div.w-full.pb-4.pt-4.px-2.py-2.flex.flex-col.justify-between.mode-lr
        [:ol.list-decimal.text-gray-500.leading-5.mgl
         (for [x (take 9 (drop (* 9 cand-page) cands))]
           [:li {:ref (:id x)} (:char_word x)])]
        [:div.flex.flex-cel.justify-between
         [:p {:class (if (= 0 cand-page) "text-gray-200" "")} "<"]
         [:p {:class (if (> total cand-page) "" "text-gray-200")} ">"]]]])))
