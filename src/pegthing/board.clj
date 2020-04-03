(ns pegthing.board
  (:require [pegthing.math :refer [tri]]
            [pegthing.player :refer [board-with-new-pos]]))

;; ASCII codes for a-z lowercase letters.
(def alpha-start 97)
(def alpha-end 123)
(def letters (map (comp str char) (range alpha-start alpha-end)))

(def positions-for-letter 3)

(defn row-tri
  "The triangular number at the end of n-th row."
  [n]
  (last (take n tri)))

(defn letter->pos
  "Convert a letter string to the corresponding position number."
  [letter]
  (inc (- (int (first letter)) alpha-start)))

(defn row-positions
  "Return all positions on the row `row-num`."
  [row-num]
  (range (inc (or (row-tri (dec row-num)) 0))
         (inc (row-tri row-num))))

(defn row-padding
  "String of spaces to add to the beginning of a row to center it."
  [row-num rows]
  (let [pad-length (/ (* (- rows row-num) positions-for-letter) 2)]
    (apply str (take pad-length (repeat " ")))))

(defn new-board
  "Create a new game board with `num-rows` rows."
  [num-rows]
  (let [initial-board {:rows num-rows}
        max-pos (row-tri num-rows)]
    (defn f
      [board pos]
      (board-with-new-pos board max-pos pos))
    (reduce f initial-board (range 1 (inc max-pos)))))
