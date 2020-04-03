(ns pegthing.ui
  (:require [clojure.string :as string]
            [pegthing.board :refer [letter->pos letters new-board row-padding row-positions]]
            [pegthing.colors :refer [colorize]]
            [pegthing.game-rules :refer [can-move?]]
            [pegthing.player :refer [make-move board-without-peg-at-pos]]))

;; we need this forward declaration to avoid an unresolved symbol.
(declare game-loop)

(defn get-input
  "Wait for the player to enter text and hit enter, then clean the input."
  ([] (get-input ""))
  ([default]
   (let [input (string/trim (read-line))]
     (if (empty? input)
       default
       (string/lower-case input)))))

(defn render-pos
  "Render the position `pos` on the board `board`."
  [board pos]
  (str (nth letters (dec pos))
       (if (get-in board [pos :pegged])
         (colorize "0" :blue)
         (colorize "-" :red))))

(defn render-row
  "Render the row number `row-num` on the board `board`."
  [board row-num]
  (str (row-padding row-num (:rows board))
       (string/join " " (map (partial render-pos board) (row-positions row-num)))))

(defn print-board
  "Print the board `board` in the terminal."
  [board]
  (doseq [row-num (range 1 (inc (:rows board)))]
    (println (render-row board row-num))))

(defn string->letters
  "Convert a string to a collection consisting of each individual character."
  [string]
  (re-seq #"[a-zA-Z]" string))

(defn prompt-empty-peg
  [board]
  (println "Here's your board:")
  (print-board board)
  (println "Remove which peg? [e]")
  (game-loop (board-without-peg-at-pos board (letter->pos (get-input "e")))))

(defn prompt-rows
  []
  (println "How many rows? [5]")
  (let [rows (Integer. (get-input 5))
        board (new-board rows)]
    (prompt-empty-peg board)))

(defn game-over
  "Game Over."
  [board]
  (let [remaining-pegs (count (filter :pegged (vals board)))]
    (println "Game over! You had" remaining-pegs "pegs left:")
    (print-board board)
    (println "Play again? y/n [y]")
    (let [input (get-input "y")]
      (if (= "y" input)
        (prompt-rows)
        (do
          (println "Bye!")
          (System/exit 0))))))

(defn play-next-turn-or-gameover
  "Let the player play another turn if there are valid moves on the board
  `board`. Otherwise it's game over."
  [board]
  (if (can-move? board)
    (game-loop board)
    (game-over board)))

(defn game-loop
  "Game loop:

  1. print in the terminal the current game board `board`;
  2. accept the player's input;
  3. try performing the move. If the move is valid, check if it's possible to
     play another turn. If it is, generate a new board and go back to 1;
     otherwise it's game over. If the move is invalid, go back to 1."
  [board]
  (println "\nHere's your board:")
  (print-board board)
  (println "Move from where to where? Enter two letters:")
  (let [input (map letter->pos (string->letters (get-input)))]
    (if-let [new-board (make-move board (first input) (second input))]
      (play-next-turn-or-gameover new-board)
      (do
        (println "\n!!! That was an invalid move :(\n")
        (game-loop board)))))
