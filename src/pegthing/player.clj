(ns pegthing.player
  (:require [clojure.string :as string]
            [pegthing.colors :refer [colorize]]
            [pegthing.connections :refer [connect-down-left connect-down-right connect-right]]
            [pegthing.math :refer [tri]]
            [pegthing.game-rules :refer [valid-move?]]))

; TODO: remove, already defined
;; ASCII codes for a-z lowercase letters.
(def alpha-start 97)
(def alpha-end 123)
(def letters (map (comp str char) (range alpha-start alpha-end)))

; TODO: remove
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

; TODO: remove
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

(defn row-tri
  "The triangular number at the end of n-th row."
  [n]
  (last (take n tri)))

; TODO: this is already defined in board.clj
(def positions-for-letter 3)

; TODO: this is already defined in board.clj
(defn row-padding
  "String of spaces to add to the beginning of a row to center it."
  [row-num rows]
  (let [pad-length (/ (* (- rows row-num) positions-for-letter) 2)]
    (apply str (take pad-length (repeat " ")))))

; TODO: this is already defined in board.clj
(defn row-positions
  "Return all positions on the row `row-num`."
  [row-num]
  (range (inc (or (row-tri (dec row-num)) 0))
         (inc (row-tri row-num))))

; TODO: this is already defined in ui.clj
(defn render-pos
  "Render the position `pos` on the board `board`."
  [board pos]
  (str (nth letters (dec pos))
       (if (get-in board [pos :pegged])
         (colorize "0" :blue)
         (colorize "-" :red))))

(defn game
  "Game"
  [num-rows]
  
  (def num-positions (row-tri num-rows))

  (defn make-board-pegged-and-connected
    [board pos]
    (def pegged-board (board-with-new-peg board pos))
    (def make-board-connected (fn [new-board connector] (connector new-board num-positions pos)))

    (reduce make-board-connected 
            pegged-board 
            [connect-right connect-down-left connect-down-right]))

  (defn make-starting-board
    []
    (reduce make-board-pegged-and-connected
            {}
            (range 1 (inc num-positions))))
  
  (defn render-row
    "Render the row number `row-num` on the board `board`."
    [board row-num]
    (str (row-padding row-num num-rows)
         (string/join " " (map (partial render-pos board) (row-positions row-num)))))

  (defn print-board
    "Print the board `board` in the terminal."
    [board]
    (doseq [row-num (range 1 (inc num-rows))]
      (println (render-row board row-num))))
  
  {:starting-board (make-starting-board)
   :num-positions num-positions
   :num-rows num-rows
   :print-board print-board})

(defn game-turn
  "Game turn"
  [board]

  ; Clojure functions can have multiple arities
  (defn board-with-peg-at-pos
    ([pos]
     (board-with-peg-at-pos board pos))
    ([new-board pos]
     (assoc-in new-board [pos :pegged] true)))

  (defn board-with-no-peg-at-pos
    ([pos]
     (board-with-no-peg-at-pos board pos))
    ([new-board pos]
     (assoc-in new-board [pos :pegged] false)))

  (defn board-with-peg-moved
    ([pos-src pos-dest]
     (board-with-peg-moved board pos-src pos-dest))
    ([new-board pos-src pos-dest]
     (board-with-peg-at-pos (board-with-no-peg-at-pos new-board pos-src) pos-dest)))

  (defn next-board-if-valid-move
    [pos-src pos-dest]
    (if-let [jumped (valid-move? board pos-src pos-dest)]
      (board-with-peg-moved (board-with-no-peg-at-pos jumped) pos-src pos-dest)))

  {:board-with-peg-at-pos board-with-peg-at-pos
   :board-with-no-peg-at-pos board-with-no-peg-at-pos
   :board-with-peg-moved board-with-peg-moved
   :next-board-if-valid-move next-board-if-valid-move})


; Example -------------------------------------------------------------------- ;
; Create a new game with 5 rows
(def g5 (game 5))
; The board game is now created, but the player still has to remove one peg. So
; let's call this game turn "turn 0"
(def turn-0 (game-turn (:starting-board g5)))
; The player removes the peg at position 4, so the game can start.
(def board-beginning-turn-1 ((:board-with-no-peg-at-pos turn-0) 4))
; A move can be valid if the peg jumps over another peg and lands in an empty
; hole. If this is true, we return the new board. Otherwise we return nil.
(def board-after-turn-1 ((:next-board-if-valid-move (game-turn board-beginning-turn-1)) 1 4))
; ---------------------------------------------------------------------------- ;