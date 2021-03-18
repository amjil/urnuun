(ns app.components.keyboard.candidate
  (:require
   [rum.core :as rum]
   [app.state :as state]))

(defn- set-position [left top node]
  (let [bounds (.getBoundingClientRect node)
        width (.-width bounds)
        height (.-height bounds)
        screen-width (.-innerWidth js/window)
        screen-height (.-innerHeight js/window)
        padding (* 16 1.2)
        style (merge
               (if (> left (- screen-width width))
                 {:left (str (- (- screen-width width) padding) "px")}
                 {:left (str (+ left padding) "px")})
               (if (> top (- screen-height height))
                 {:top (str (- screen-height height) "px")}
                 {:top (str top "px")}))]
    (js/console.log "screen-height" screen-height)
    style))

(rum/defcs view < rum/reactive
  [state]
  (rum/local nil ::node)
  (let [cands (state/sub :ime/candidate)
        cand-page (state/sub :ime/candidate-page)
        total-record (count cands)
        total (- (int (/ total-record 9)) (if (zero? (mod total-record 9)) 1 0))
        input-str (state/sub :ime/input)
        compo     (:rum/react-component state)
        node (js/ReactDOM.findDOMNode compo)
        ;; node (::node state)
        left (state/sub :ime/candidate-left)
        top (state/sub :ime/candidate-top)
        style (if node (set-position left top node) {})]
    (js/console.log ">>>>>>>> " state)
    (if-not (empty? input-str)
      [:div.z-10.border-purple-100.border.bg-gray-50.rounded-xl.shadow-xl.absolute.justify-between.flex.flex-col.divide-y.top-6.left-48
       {:style style}
       [:p.pl-3.text-gray-500.text-xs input-str]
       [:div.w-full.pb-4.pt-4.px-2.py-2.flex.flex-col.justify-between.mode-lr
        [:ol.list-decimal.text-gray-500.leading-5.mgl
         (for [x (take 9 (drop (* 9 cand-page) cands))]
           [:li {:ref (:id x)} (:char_word x)])]
        [:div.flex.flex-cel.justify-between
         [:p {:ref "cand-prev-page" :class (if (= 0 cand-page) "text-gray-200" "")} "<"]
         [:p {:ref "cand-next-page" :class (if (> total cand-page) "" "text-gray-200")} ">"]]]])))
