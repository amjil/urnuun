(ns app.components.input
  (:require
   [rum.core :as rum]))

(rum/defc input < rum/reactive
  [ id label value opts]
  [:div.flex.flex-cel.text-left.mgl.pr-6
   [:p.pb-4.text-purple-600 label]
   [:div#id.mousetrap.w-auto.h-full.border.border-purple-600.rounded-md.focus:outline-none.focus:ring-2.focus:ring-purple-600.focus:border-transparent.align-middle.single-line
    (merge opts {:contenteditable "true"})
    value]])

(def weeks ["ᠭᠠᠷᠠᠭ ᠤᠨ ᠡᠳᠦᠷ" "ᠭᠠᠷᠠᠭ ᠤᠨ ᠨᠢᠭᠡᠨ" "ᠭᠠᠷᠠᠭ ᠤᠨ ᠬᠣᠶᠠᠷ" "ᠭᠠᠷᠠᠭ ᠤᠨ ᠭᠤᠷᠪᠠᠨ" "ᠭᠠᠷᠠᠭ ᠤᠨ ᠳᠥᠷᠪᠡᠨ" "ᠭᠠᠷᠠᠭ ᠤᠨ ᠲᠠᠪᠤᠨ" "ᠭᠠᠷᠠᠭ ᠤᠨ ᠵᠢᠷᠭᠤᠭᠠᠨ"])
(defn date-picker []
  [:div.relative.mgl.ml-6.text-left
   [:div.w-full.h-full.pt-14.pb-4.border.border-purple-600.rounded-md.focus:outline-none.focus::ring-2.focus:ring-purple-600.focus:border-transparent.align-middle.single-line
    [:.absolute.top-0.left-0.py-3.align-middle.w-full
     [:svg.w-full.text-purple-400
      {:fill "none" :viewBox "0 0 24 24" :stroke "currentColor"}
      [:path {:stroke-linecap "round" :stroke-linejoin "round" :stroke-width "2" :d "M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"}]]]

    [:.bg-white.ml-10.rounded-lg.shadow.p-4.absolute.top-0.left-0.h-80
     [:.flex.justify-between.items-center.mr-2
      [:div
       [:span.mt-1.text-lg.text-purple-600.font-normal
        {:style {:text-orientation "upright"}} "2021"]
       [:span.text-lg.font-bold.text-purple-800 " \\"]
       [:span.text-lg.font-bold.text-purple-800 {:style {:text-orientation "upright"}} " 3"]
       [:span.text-lg.font-bold.text-purple-800 " \\"]
       [:span.text-lg.font-bold.text-purple-800 {:style {:text-orientation "upright"}} " 3"]]
      [:div
       [:button.transition.ease-in-out.duration-100.inline-flex.cursor-pointer.hover:bg-gray-200.p-1.rounded-full
        [:svg.h-6.w-6.text-gray-500.inline-flex.transform.rotate-90
         {:fill "none" :viewBox "0 0 24 24" :stroke "currentColor"}
         [:path {:stroke-linecap "round" :stroke-linejoin "round" :stroke-width "2" :d "M15 19l-7-7 7-7"}]]]
       [:button.transition.ease-in-out.duration-100.inline-flex.cursor-pointer.hover:bg-gray-200.p-1.rounded-full
        [:svg.h-6.w-6.text-gray-500.inline-flex.transform.rotate-90
         {:fill "none" :viewBox "0 0 24 24" :stroke "currentColor"}
         [:path {:stroke-linecap "round" :stroke-linejoin "round" :stroke-width "2" :d "M9 5l7 7-7 7"}]]]]]
     [:.divide-x.divide-purple-50
      [:.flex.flex-wrap.-my-3.mr-3
       (for [i weeks]
         [:.py-1 {:style {:height "14.26%"}}
          [:.text-purple-800.text-center.text-xs
           {:ref i}
           i]])]
      [:.flex.flex-wrap.-my-3.ml-3
       [:.text-center.border.p-1.border-transparent.text-sm {:style {:height "14.26%"}}]
       (for [i (range 1 32)]
         [:.py-1.ml-1 {:style {:height "14.26%"}}
          [:.cursor-pointer.text-center.text-sm.leading-none.rounded-full.leading-loose.transition.ease-in-out.duration-100.text-gray-700.hover:bg-blue-200
           {:ref i :style {:text-combine-upright "digits 2" :-webkit-text-combine "horizontal"}}
           i]])]]]
    [:span.w-full "bbb"]]])

(rum/defc textarea < rum/reactive
  [id label value opts]
  [:div.flex.flex-cel.text-left.mgl.pr-6
   [:p.pb-4.text-purple-600 label]
   [:div#id.mousetrap.w-40.max-w-full.h-full.border.border-purple-600.rounded-md.focus:outline-none.focus:ring-2.focus:ring-purple-600.focus:border-transparent.align-middle
    (merge opts {:contenteditable "true"})
    value]])

(defn checkbox
  [option]
  [:input.form-checkbox.h-4.w-4.transition.duration-150.ease-in-out
   (merge {:type "checkbox"} option)])

(defn toggle
  ([label on? on-click] (toggle label on? on-click false))
  ([label on? on-click small?]
  [:div.flex.flex-cel.text-left.mgl.pr-6
   [:p.pb-4.text-purple-600 label]
   [:a {:on-click on-click}
    [:span.relative.inline-block.flex-shrink-0.w-6.h-11.border-2.border-transparent.flex.flex-col.rounded-full.cursor-pointer.focus:outline-none.focus:shadow-outline.transition-colors.ease-in-out.duration-200
     {:aria-checked "false", :tab-index "0", :role "checkbox"
      :class        (if on? "bg-indigo-600" "bg-gray-200")}
     [:span.inline-block.h-5.w-5.rounded-full.bg-white.shadow.transform.transition.ease-in-out.duration-200
      {:class       (if on? "translate-y-5" "translate-y-0")
       :aria-hidden "true"}]]]]))

(comment
  (require '[app.state :as state])
  (state/sub :test)

  (state/update-state! :test not)

  (def date (js/Date. 2021 3))
  (Date. 2021 3)
  date
  (def day-of-week (.getDay date))
  day-of-week

  ;; month with days
  (def date1 (js/Date. 2021  3 0))
  date1
  (.getDate date1)
  ;; month start of week
  (def date2 (js/Date. 2021 2))
  date2
  (.getDay date2)
  
  (range 1 32)
  (concat [nil] (range 2)))