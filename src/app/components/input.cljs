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

  (state/update-state! :test not))