(ns app.components.editor
  (:require
   [rum.core :as rum]
   ["quill" :as quill]
   [app.state :as state]
   [goog.dom.classes :as gclass]
   [goog.object :as gobj]
   [goog.style :as gstyle]
   ["/app/caret_pos" :as caret-pos]))

(defn set-position [position]
  (state/set-state! :ime/candidate-left (+ (.-left position) (* 16 1.2)))
  (state/set-state! :ime/candidate-top (.-top position)))

(rum/defc editor <
  {:did-mount (fn [state]
                 (let [node (rum/dom-node state)
                       qeditor (quill. node
                                       (clj->js  {:modules     {:toolbar  false
                                                                ;; :keyboard {:bindings {:backspace {:key     8
                                                                ;;                                   :handler (fn [] nil)}}}
                                                                }
                                                  :theme       "snow"
                                            ;; :readOnly true
                                            ;; :debug "info"
                                                  :placeholder "ᠠᠭᠤᠯᠭ᠎ᠠ ᠪᠠᠨ ᠨᠠᠢᠷᠠᠭᠤᠯᠤᠶ᠎ᠠ ..."}))
                       child-node (aget (.-children node) 0)
                       clipboard-node (aget (.-children node) 1)]
                   (gclass/add child-node "mousetrap resize-x")
                   (gclass/add child-node "h-full max-w-full overflow-x-scroll border border-purple-600 rounded-md focus:outline-none focus:ring-2 focus:ring-purple-600 focus:border-transparent")
                   (aset (.-style clipboard-node) "display" "none")

                   (state/set-state! :editor qeditor)
                   (.on qeditor "selection-change"
                        (fn [range oldrange source]
                          (set-position ((gobj/get caret-pos "offset") child-node))))
                   (.on qeditor "text-change"
                        (fn [delta olddelta source]
                          (let [range (.getSelection qeditor)]
                            (set-position ((gobj/get caret-pos "offset") child-node)))))))}
  
  [state id content on-change-fn]
  [:div.quill-editor.h-full.min-w-max.mgl.text-gray-700
   {:id id
    :dangerouslySetInnerHTML {:__html content}}])


(comment
  (require '["/app/caret_pos" :as caret-pos] :reload)
  caret-pos
  (gobj/get caret-pos "position")
  (def edit (js/document.querySelector ".ql-editor"))
  edit
  (js/console.log (.getBoundingClientRect edit))
  (.-contentEditable edit)
  (.-tagName edit)
  (.toUpperCase (.-tagName edit))
  ((gobj/get caret-pos "getOffset") edit)
  ((gobj/get caret-pos "position") edit)
  ((gobj/get caret-pos "offset") edit)

  (.-height js/window.screen)
  (.-width js/window.screen)

  (require '[goog.style :as gstyle] :reload)
  gstyle

  (state/sub :editor)
  
  (def edit (js/document.querySelector ".aaa"))
  edit 
  (js/console.log edit)
  (js/console.log (.text edit))
  (js/console.log "aaaa")
  (.-innerText edit)
  )