(ns app.handler.keyboard.event
  (:require
   [rum.core :as rum]
   ["mousetrap" :as mousetrap]
   [goog.object :as gobj]
   [app.state :as state]
   [app.handler.keyboard.api :as api]))

(def bind! (gobj/get mousetrap "bind"))

(defn on-key-down [k e]
  (when (state/sub :ime/active?)
    (.preventDefault e)
    (let [input-str (state/sub :ime/input)
          input-new (str input-str k)]
      (js/console.log "key pressed " k)
      (js/console.log "input-str = " input-new)
      (state/set-state! :ime/input input-new)
      (api/candidate input-new #(state/set-state! :ime/candidate %)))))

(defn on-delete []
  (when (state/sub :ime/active?)
    (when-not (empty? (state/sub :ime/input))
      (let [input-str (state/sub :ime/input)]
        (state/update-state! :ime/input #(subs % 0 (dec (count input-str))))
        (if (< 1 (count input-str))
          (api/candidate (subs input-str 0 (dec (count input-str))) #(state/set-state! :ime/candidate %)))))))

(defn on-plus []
  (when (state/sub :ime/active?)
    (let [cands (state/sub :ime/candidate)
          current-page (state/sub :ime/candidate-page)
          total-record (count cands)
          total (- (int (/ total-record 9)) (if (zero? (mod total-record 9)) 1 0))]
      (when (and (> total 0) (> total current-page))
        (state/set-state! :ime/candidate-page (inc current-page))))))

(defn on-minus []
  (when (state/sub :ime/active?)
    (let [cands (state/sub :ime/candidate)
          current-page (state/sub :ime/candidate-page)
          total-record (count cands)
          total (- (int (/ total-record 9)) (if (zero? (mod total-record 9)) 1 0))]
      (when (> current-page 0)
        (state/set-state! :ime/candidate-page (dec current-page))))))

(defn on-space []
  (when (state/sub :ime/active?)
    nil))

(def keyboard 
  {"a" #(on-key-down "a" %)
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
   "-" on-minus})

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

)
  
