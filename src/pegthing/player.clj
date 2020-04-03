(ns pegthing.player
  (:require [pegthing.connections :refer [connect-down-left connect-down-right connect-right]]
            [pegthing.game-rules :refer [valid-move?]]))

(defn remove-peg
  "Take the peg at a given position out of the game board."
  [board pos]
  (assoc-in board [pos :pegged] false))

(defn place-peg
  "Place a peg on the game board at a given position."
  [board pos]
  (assoc-in board [pos :pegged] true))

(defn move-peg
  "Take the peg out of p1 and place it in p2."
  [board p1 p2]
  (place-peg (remove-peg board p1) p2))

(defn make-move
  "Move peg from p1 to p2, removing jumped peg."
  [board p1 p2]
  (if-let [jumped (valid-move? board p1 p2)]
    (move-peg (remove-peg board jumped) p1 p2)))

(defn add-pos
  "Peg the position and perform connections."
  [board max-pos pos]
  (let [pegged-board (assoc-in board [pos :pegged] true)]
    (reduce (fn [new-board connector] (connector new-board max-pos pos))
            pegged-board
            [connect-right connect-down-left connect-down-right])))
