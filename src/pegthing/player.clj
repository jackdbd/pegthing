(ns pegthing.player
  (:require [pegthing.connections :refer [connect-down-left connect-down-right connect-right]]
            [pegthing.game-rules :refer [valid-move?]]))

(defn board-without-peg-at-pos
  "Create a new board starting from the `board` board and remove the peg at
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

(defn make-move
  "Move peg from p1 to p2, removing jumped peg."
  [board p1 p2]
  (if-let [jumped (valid-move? board p1 p2)]
    (board-with-moved-peg (board-without-peg-at-pos board jumped) p1 p2)))

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
