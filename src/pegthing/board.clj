(ns pegthing.board
  (:require
   [clojure.spec.alpha :as s]
   [pegthing.connections :refer [connect-down-left connect-down-right connect-right]]
   [pegthing.math :refer [nth-tri]]
   [pegthing.utils :refer [ex-info-helper]]))

(s/def ::num-rows pos?)
(s/def ::board (s/keys :req [::num-rows]))
(s/def ::pegged boolean?)

;; ASCII codes for a-z lowercase letters.
(def alpha-start 97)
(def alpha-end 123)
(def letters (map (comp str char) (range alpha-start alpha-end)))
(def positions-for-letter 3)

(defn pegged?
  "Is the hole at position `pos` on the board `board` filled with a peg?"
  [board pos]
  (get-in board [pos :pegged]))

(defn connections-at-pos
  "All connections between the position `pos` and the other holes on the board."
  [board pos]
  (get-in board [pos :connections]))

(defn letter->pos
  "Convert a letter string to the corresponding position number."
  [letter]
  (inc (- (int (first letter)) alpha-start)))

(defn row-num->positions
  "Return all positions on the row `row-num`."
  [row-num]
  (range (inc (or (nth-tri (dec row-num)) 0))
         (inc (nth-tri row-num))))

(defn board-without-peg-at-pos
  "Create a new board starting from the `board` board, then remove the peg at
  position `pos`."
  [board pos]
  (assoc-in board [pos :pegged] false))

(defn board-with-new-peg
  "Create a new board starting from the `board` board and peg the hole at
  position `pos`."
  [board pos]
  (assoc-in board [pos :pegged] true))

(defn board-with-moved-peg
  "Create a new board starting from the `board `board and move the peg at
  position `p1` to position `p2`."
  [board p1 p2]
  (board-with-new-peg (board-without-peg-at-pos board p1) p2))

(defn board-with-new-pos
  "Create a new board starting from the `board` board and adding a pegged hole
  at position `pos`. The new board will have `max-pos` positions."
  [board max-pos pos]
  ; Immediately place a peg at position `pos`.
  (let [pegged-board (board-with-new-peg board pos)]
    ; Then compute all connections and generate the new board.
    (reduce (fn [new-board connector] (connector new-board max-pos pos))
            pegged-board
            [connect-right connect-down-left connect-down-right])))

(defn- make-f
  "Create the reducer function for the game board.
   The reducer function takes a `board` and produces a new board that has one
   more position: `pos`"
  [max-pos]
  (fn [board pos]
    (board-with-new-pos board max-pos pos)))

(defn new-board
  "Create a new game board with `num-rows` rows."
  [num-rows]
  (if-not (s/valid? ::num-rows num-rows)
    (throw (ex-info-helper ::num-rows num-rows))
    (let [val {::num-rows num-rows}
          max-pos (nth-tri num-rows)
          f (make-f max-pos)
          coll (range 1 (inc max-pos))]
      (reduce f val coll))))
