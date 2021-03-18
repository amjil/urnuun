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
                                       #js {:modules #js {:toolbar false}
                                            :theme "snow"
                                            ;; :readOnly true
                                            ;; :debug "info"
                                            :placeholder "ᠠᠭᠤᠯᠭ᠎ᠠ ᠪᠠᠨ ᠨᠠᠢᠷᠠᠭᠤᠯᠤᠶ᠎ᠠ ..."})
                       child-node (aget (.-children node) 0)
                       clipboard-node (aget (.-children node) 1)]
                   (gclass/add child-node "mousetrap")
                   (aset (.-style clipboard-node) "display" "none")
                   (.on qeditor "selection-change"
                        (fn [range oldrange source]
                          (set-position ((gobj/get caret-pos "offset") child-node))))
                   (.on qeditor "text-change"
                        (fn [delta olddelta source]
                          (let [range (.getSelection qeditor)]
                            (set-position ((gobj/get caret-pos "offset") child-node)))))))}
  
  [state id content on-change-fn]
  [:div.quill-editor.h-full.min-w-min
   {:id id
    :dangerouslySetInnerHTML {:__html content}}])


(comment
  (require '["/app/caret_pos" :as caret-pos] :reload)
  caret-pos
  (gobj/get caret-pos "position")
  (def edit (js/document.querySelector ".ql-editor"))
  edit
  (js/console.log (.getBoundingClientRect edit))
  ((gobj/get caret-pos "getOffset") edit)
  ((gobj/get caret-pos "position") edit)
  ((gobj/get caret-pos "offset") edit)

  (.-height js/window.screen)
  (.-width js/window.screen)

  (require '[goog.style :as gstyle] :reload)
  gstyle

  )