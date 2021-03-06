(ns app.handler.keyboard.event
  (:require
   [rum.core :as rum]
   ["mousetrap" :as mousetrap]
   [goog.object :as gobj]
   [app.state :as state]
   [app.handler.keyboard.api :as api]))

(def bind! (gobj/get mousetrap "bind"))

(defn check-is-input [el]
  (or (= "true" (.-contentEditable el))
      (= "INPUT" (.toUpperCase (.-tagName el)))
      (= "TEXTAREA" (.toUpperCase (.-tagName el)))))

(defn is-contenteditable [el]
  (= "true" (.-contentEditable el)))

(defn next-words [cand]
  (api/next-words (:bqr_biclg cand) (:id cand) #(state/set-state! :ime/candidate %)))

(defn insert-text [e value]
  (if (is-contenteditable (.-target e))
    (if-let [quill (state/sub :editor)]
      (let [range (.getSelection quill)
            value (if (or (zero? (.-index range))
                          (some #{(.getText quill (- (.-index range) 1) 1)} [" " " " "\n" "\r"])
                          (some #{(first value)} [" " " "]))
                    value
                    (str " " value))]
        (.insertText quill (.-index range) (str "" value))))))

(defn on-key-down [k e]
  (when (and (check-is-input (.-target e)) (state/sub :ime/active?))
    (.preventDefault e)
    (let [input-str (state/sub :ime/input)
          input-new (str input-str k)]
      (state/set-state! :ime/input input-new)
      (api/candidate input-new #(state/set-state! :ime/candidate %)))))

(defn on-delete [e]
  (when (and (check-is-input (.-target e)) (state/sub :ime/active?))
    (when (not-empty (state/sub :ime/input))
      (.preventDefault e)
      (let [input-str (state/sub :ime/input)]
        (state/update-state! :ime/input #(subs % 0 (dec (count input-str))))
        (when (< 1 (count input-str))
          (api/candidate (subs input-str 0 (dec (count input-str))) #(state/set-state! :ime/candidate %)))))))

(defn on-plus [e]
  (when (and (check-is-input (.-target e)) (state/sub :ime/active?))
    (.preventDefault e)
    (let [cands (state/sub :ime/candidate)
          current-page (state/sub :ime/candidate-page)
          total-record (count cands)
          total (- (int (/ total-record 9)) (if (zero? (mod total-record 9)) 1 0))]
      (when (and (> total 0) (> total current-page))
        (state/set-state! :ime/candidate-page (inc current-page))))))

(defn on-minus [e]
  (when (and (check-is-input (.-target e)) (state/sub :ime/active?))
    (.preventDefault e)
    (let [cands (state/sub :ime/candidate)
          current-page (state/sub :ime/candidate-page)
          total-record (count cands)
          total (- (int (/ total-record 9)) (if (zero? (mod total-record 9)) 1 0))]
      (when (> current-page 0)
        (state/set-state! :ime/candidate-page (dec current-page))))))

(defn on-esc [e]
  (when (and (check-is-input (.-target e)) (state/sub :ime/active?))
    (.preventDefault e)
    (state/set-state! :ime/candidate [])
    (state/set-state! :ime/input "")))

(defn on-space [e]
  (when (and (check-is-input (.-target e)) (state/sub :ime/active?))
    (let [cands (state/sub :ime/candidate)
          page (state/sub :ime/candidate-page)]
      (when-not (empty? cands)
        (.preventDefault e)
        (let [cand (first (drop (* 9 page) cands))]
          (insert-text e (:char_word cand))
          (next-words cand)
          (state/set-state! :ime/input "")
          (state/set-state! :ime/candidate-page 0))))))

(defn on-num-key [k e]
  (when (and (check-is-input (.-target e)) (state/sub :ime/active?))
    (let [cands (state/sub :ime/candidate)
          page (state/sub :ime/candidate-page)]
      (when-not (empty? cands)
        (.preventDefault e)
        (let [cands (drop (* 9 page) cands)
              cand (if (> (count cands) k) 
                     (or (nth cands k) (first cands))
                     (first cands))]
          (insert-text e (:char_word cand))
          (next-words cand)
          (state/set-state! :ime/input "")
          (state/set-state! :ime/candidate-page 0))))))

(defn on-toggle-inputmethod [e]
  (.preventDefault e)
  (state/update-state! :ime/active? not)
  (js/console.log ":ime/active? " (state/sub :ime/active?)))

(defn on-return [e]
  (.preventDefault e)
  (insert-text e "\n")
  (insert-text e " "))

(def keyboard 
  {
   "1" #(on-num-key 0 %)
   "2" #(on-num-key 1 %)
   "3" #(on-num-key 2 %)
   "4" #(on-num-key 3 %)
   "5" #(on-num-key 4 %)
   "6" #(on-num-key 5 %)
   "7" #(on-num-key 6 %)
   "8" #(on-num-key 7 %)
   "9" #(on-num-key 8 %)
   "a" #(on-key-down "a" %)
   "b" #(on-key-down "b" %)
   "c" #(on-key-down "c" %)
   "d" #(on-key-down "d" %)
   "e" #(on-key-down "e" %)
   "f" #(on-key-down "f" %)
   "g" #(on-key-down "g" %)
   "h" #(on-key-down "h" %)
   "i" #(on-key-down "i" %)
   "j" #(on-key-down "j" %)
   "k" #(on-key-down "k" %)
   "l" #(on-key-down "l" %)
   "m" #(on-key-down "m" %)
   "n" #(on-key-down "n" %)
   "o" #(on-key-down "o" %)
   "p" #(on-key-down "p" %)
   "q" #(on-key-down "q" %)
   "r" #(on-key-down "r" %)
   "s" #(on-key-down "s" %)
   "t" #(on-key-down "t" %)
   "u" #(on-key-down "u" %)
   "v" #(on-key-down "v" %)
   "w" #(on-key-down "w" %)
   "x" #(on-key-down "x" %)
   "y" #(on-key-down "y" %)
   "z" #(on-key-down "z" %)
   "backspace" on-delete
   "=" on-plus
   "-" on-minus
   "esc" on-esc
   "space" on-space
   "ctrl+`" on-toggle-inputmethod
   })

(defn bind-keyboard!
  []
  (doseq [[k f] keyboard]
    (bind! k f)))

(comment
  (require '[app.state :as state] :reload)
  state/update-state!
  (state/update-state! :ime/input #(str % "c"))
  (require '["mousetrap" :as mousetrap])
  (require '[goog.object :as gobj])
  mousetrap
  bind!
  (def bind! (gobj/get mousetrap "bind"))
  (bind-keyboard!)

  keyboard
  on-key-down
  (on-key-down "a")
  (js/console.log "aa")
  state/state
  (state/sub :ime/active?)

  (bind! "a" #(on-key-down "a"))
  (state/set-state! :ime/input "")
  (state/set-state! :ime/candidate)
  (subs "abc" 0 (dec (count "abc")))
  (bind! "backspace" on-delete)
  (bind! "=" on-plus)
  (on-plus)
  (bind! "plus" on-plus)
  (bind! "-" on-minus)


  
  (js/console.log (.getSelection js/window)
                  )
  (def sel (.getSelection js/window))
  (def range (.getRangeAt sel 0))
  (def  quill (state/sub :editor))
  (.-keyboard quill))
  
