(ns pegthing.game-rules
  "Rules and logic for the Pegthing game."
  (:require [pegthing.board :refer [connections-at-pos pegged?]]))

(defn valid-moves
  "Valid moves on the board `board` for the position `pos`.
  The return value is a map. For each entry, the key is the destination and the
  value is the jumped position."
  [board pos]
  (into {}
        (filter (fn [[destination jumped]]
                  (and (not (pegged? board destination))
                       (pegged? board jumped)))
                (connections-at-pos board pos))))

(defn valid-move?
  "Check whether the move from `p1` to `p2` is valid or not.
  Is the move from position `p1` to position `p2` valid, given that the player
  is playing on the board `board`?
  A move can be valid if the peg jumps over another peg and lands in an empty
  hole.
  Return jumped position if the move is valid, nil otherwise."
  [board p1 p2]
  (get (valid-moves board p1) p2))

(defn can-move?
  "Do any of the pegs on the board `board` have valid moves?"
  [board]
  (some (comp not-empty (partial valid-moves board))
        (map first (filter #(get (second %) :pegged) board))))
