(ns app.core
  (:require
   [rum.core :as rum]
   [app.components.main :as main]
   [app.components.keyboard.candidate :as candidate]
   [app.components.editor :as editor]
   [app.components.input :as input]
   [app.state :as state]
   [app.handler.keyboard.event :as key-event]))

(defonce app-state (atom {:text "Hello world!"}))

(rum/defcs page
  <
  rum/reactive
  {:did-mount (fn [state]
                (key-event/bind-keyboard!)
                state)}
  []
  [:div.flex.flex-cel.min-h-screen
   [:div.mode-lr.top-0.left-0.fixed.h-full.w-16 
    [:div.py-5.translate-x-1.bg-white.border-opacity-100.border-r.border-gray-50.shadow-md.h-full.w-full.flex.flex-col.justify-center.overflow-hidden
     [:a.hidden.md:flex.items-center.w-full.mb-auto
      [:img.w-14.transform.rotate-90 {:src "/img/logo.svg"}]
      [:div.text-base.font-thin.mt-4
       [:span.text-xl.text-purple-700 "ᠶᠠᠭᠤ ᠪᠣᠢ "]
       [:span.text-sm.text-gray-500 "ᠶᠠᠭᠤ ᠪᠣᠢ ᠶᠠᠭᠤ"]]]]]
   [:div.flex-grow.md:pl-16
    [:div.bg-gradient-to-r.from-light-blue-50.to-light-blue-100
     [:div.p-4.container.my-auto.h-full.w-full.mode-lr
    ;;  [:div.p-4.container.my-auto.h-full.w-full
      (input/input "idd" "ᠨᠡᠷ᠎ᠡ:" "bbbb" {})
      (input/textarea "desc" "ᠲᠠᠨᠢᠯᠴᠠᠭᠤᠯᠭ᠎ᠠ:" "bbbb" {})
      (input/toggle "ᠵᠥᠪ:" (state/sub :test) #(state/update-state! :test not))
      [:.grid.grid-cols-3.gap-4
       [:div.bg-purple-500.w-12.rounded-md.flex.items-center.justify-center.text-white.text-2xl.font-extrabold 1]
       [:div.bg-purple-500.w-12.rounded-md.flex.items-center.justify-center.text-white.text-2xl.font-extrabold 2]
       [:div.bg-purple-500.w-12.rounded-md.flex.items-center.justify-center.text-white.text-2xl.font-extrabold 3]
       [:div.bg-purple-500.w-12.rounded-md.flex.items-center.justify-center.text-white.text-2xl.font-extrabold 4]
       [:div.bg-purple-500.w-12.rounded-md.flex.items-center.justify-center.text-white.text-2xl.font-extrabold 5]
       [:div.bg-purple-500.w-12.rounded-md.flex.items-center.justify-center.text-white.text-2xl.font-extrabold 6]
       [:div.bg-purple-500.w-12.rounded-md.flex.items-center.justify-center.text-white.text-2xl.font-extrabold 7]
       [:div.bg-purple-500.w-12.rounded-md.flex.items-center.justify-center.text-white.text-2xl.font-extrabold 8]
       [:div.bg-purple-500.w-12.rounded-md.flex.items-center.justify-center.text-white.text-2xl.font-extrabold 9]]
      [:.grid.grid-cols-9.gap-4.pl-4
       [:.col-span-2.bg-purple-500.w-12.rounded-md.flex.items-center.justify-center.text-white.text-2xl.font-extrabold 1]
       [:.col-span-7.bg-purple-500.w-12.rounded-md.flex.items-center.justify-center.text-white.text-2xl.font-extrabold 2]]
      (input/date-picker)
      [:.grid.grid-cols-9.gap-4.pl-4
       [:.col-span-2.bg-purple-500.w-12.rounded-md.flex.items-center.justify-center.text-white.text-2xl.font-extrabold 1]
       [:.col-span-7.bg-purple-500.w-12.rounded-md.flex.items-center.justify-center.text-white.text-2xl.font-extrabold 2]]
      ;; [:h1 (:text @app-state)]
      ;; [:h3 "Edit this and watch it change"]
      ;; (editor/editor "quill-id" "content" nil)
      ]
     

     ; (main/timer)
     (candidate/view)]]])

(defn start []
  ;; start is called by init and after code reloading finishes
  ;; this is controlled by the :after-load in the config
  (rum/mount (page)
             (. js/document (getElementById "app"))))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (start))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (js/console.log "stop"))
