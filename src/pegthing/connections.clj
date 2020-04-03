(ns pegthing.connections
  (:require [pegthing.math :refer [tri triangular?]]))

(defn in-bounds?
  "Is every position less than or equal the max position?"
  [max-pos & positions]
  (= max-pos (apply max max-pos positions)))

(defn connect
  "Form a mutual connection between two positions"
  [board max-pos pos neighbor destination]
  (if (in-bounds? max-pos neighbor destination)
    (reduce (fn [new-board [p1 p2]] (assoc-in new-board [p1 :connections p2] neighbor))
            board
            [[pos destination] [destination pos]])
    board))

(defn connect-right
  [board max-pos pos]
  (let [neighbor (inc pos)
        destination (inc neighbor)]
    (if-not (or (triangular? neighbor) (triangular? pos))
      (connect board max-pos pos neighbor destination)
      board)))

(defn pos->row-num
  "Convert a position `pos` to the row number the position belongs to.
  E.g. pos 1 is row 1; pos 2 and pos 3 are in row 2, etc"
  [pos]
  (inc (count (take-while #(> pos %) tri))))

(defn connect-down-left
  [board max-pos pos]
  (let [row (pos->row-num pos)
        neighbor (+ row pos)
        destination (+ 1 row neighbor)]
    (connect board max-pos pos neighbor destination)))

(defn connect-down-right
  [board max-pos pos]
  (let [row (pos->row-num pos)
        neighbor (+ 1 row pos)
        destination (+ 2 row neighbor)]
    (connect board max-pos pos neighbor destination)))
