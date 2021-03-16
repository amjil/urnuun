(ns app.core
  (:require
   [rum.core :as rum]
   [app.components.main :as main]
   [app.components.keyboard.candidate :as candidate]
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
   [:header.mode-lr "navigation bar"]
   [:div.flex-grow
    [:div.bg-gradient-to-r.from-light-blue-50.to-light-blue-100
     [:h1 (:text @app-state)]
     [:h3 "Edit this and watch it change"]
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
