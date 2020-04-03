(ns pegthing.game-rules
  "Rules and logic for the Peg Thing game.")

(defn pegged?
  "Is the hole at position `pos` on the board `board` filled with a peg?"
  [board pos]
  (get-in board [pos :pegged]))

(defn valid-moves
  "Valid moves on the board `board` for the position `pos`.
  The return value is a map. For each entry, the key is the destination and the
  value is the jumped position."
  [board pos]
  (into {}
        (filter (fn [[destination jumped]]
                  (and (not (pegged? board destination))
                       (pegged? board jumped)))
                (get-in board [pos :connections]))))

(defn valid-move?
  "Is the move from position `p1` to position `p2` valid, given that we are on
  the board `board`?
  Return jumped position if the move from p1 to p2 is valid, nil otherwise."
  [board p1 p2]
  (get (valid-moves board p1) p2))

(defn can-move?
  "Do any of the pegs on the board `board` have valid moves?"
  [board]
  (some (comp not-empty (partial valid-moves board))
        (map first (filter #(get (second %) :pegged) board))))
