(ns app.components.editor
  (:require
   [rum.core :as rum]
   ["quill" :as quill]
   [goog.dom.classes :as gclass]))

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
                   (aset (.-style clipboard-node) "display" "none")))}
  [state id content on-change-fn]
  [:div.quill-editor.mousetrap.h-full.min-w-min
   {:id id
    :dangerouslySetInnerHTML {:__html content}}])