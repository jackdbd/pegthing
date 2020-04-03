(ns pegthing.player
  (:require
   [pegthing.board :refer [board-with-moved-peg board-without-peg-at-pos]]
   [pegthing.game-rules :refer [valid-move?]]))

(defn move
  "Move peg from p1 to p2, removing jumped peg."
  [board p1 p2]
  (when-let [jumped (valid-move? board p1 p2)]
    (board-with-moved-peg (board-without-peg-at-pos board jumped) p1 p2)))

(defn make-move-from-to
  "aa"
  [board]
  (fn [p1 p2]
    (move board p1 p2)))
