(ns pegthing.prompt
  (:require
   [pegthing.board :refer [board-without-peg-at-pos letter->pos new-board]]
   [pegthing.game :refer [game-loop]]
   [pegthing.renderer :refer [print-board]]
   [pegthing.utils :refer [get-input]]))

;; we need this forward declaration to avoid an unresolved symbol.
(declare new-game)

(def notify {:error (fn [msg] (println msg))
             :game-over (fn [board remaining-pegs]
                          (println "Game over! You had" remaining-pegs "pegs left:")
                          (print-board board)
                          (println "Play again? y/n [y]"))
             :new-game (fn [] (new-game))
             :begin-loop (fn [board]
                           (println "\nHere's your board:")
                           (print-board board)
                           (println "Move from where to where? Enter two letters (or press q to quit):"))
             :quit-game (fn []
                          (println "Bye!")
                          (System/exit 0))})

(defn- new-turn
  [board]
  (println "Here's your board:")
  (print-board board)
  (println "Remove which peg? [e]")
  (let [letter (get-input "e")
        pos (letter->pos letter)]
    (game-loop (board-without-peg-at-pos board pos) notify)))

(defn new-game
  "Ask the player to pick a number of rows to play the game with."
  []
  (println "How many rows? [5]")
  (let [input (get-input "5")]
    (try
      (let [num-rows (Integer/valueOf ^String input)]
        (new-turn (new-board num-rows)))
      (catch IndexOutOfBoundsException _
        (println "!!! Invalid input. Pick an integer between [1-6] (there are not enough letters for the 7th row)")
        (new-game))
      (catch Exception _
        (println "!!! Invalid input. Pick a positive integer")
        (new-game)))))
