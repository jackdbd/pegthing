(ns pegthing.game
  (:require
   [pegthing.board :refer [letter->pos]]
   [pegthing.game-rules :refer [can-move?]]
   [pegthing.player :refer [move]]
   [pegthing.utils :refer [get-input string->letters]]))

;; we need this forward declaration to avoid an unresolved symbol.
(declare game-loop)

(defn- game-over
  "Game Over."
  [board notify]
  (let [remaining-pegs (count (filter :pegthing.board/board (vals board)))]
    ((:game-over notify) board remaining-pegs)
    (let [input (get-input "y")]
      (if (= "y" input)
        (:new-game notify)
        (:quit-game notify)))))

(defn- play-next-turn-or-gameover
  "Let the player play another turn if there are valid moves on the board
  `board`. Otherwise it's game over."
  [board notify]
  (if (can-move? board)
    (game-loop board notify)
    (game-over board notify)))

(defn game-loop
  "Game loop:

  1. notify the caller about the initial state of the board;
  2. accept the player's input. If the input is invalid, go back to 1;
  3. try performing the move. If the move is valid, check if it's possible to
     play another turn. If it is, generate a new board and go back to 1;
     otherwise it's game over. If the move is invalid, go back to 1."
  [board notify]
  ((:begin-loop notify) board)
  (let [input (get-input "")]
    (if (and (= 1 (count input)) (= "q" (subs input 0 1)))
      (:quit-game notify)
      (let [letters (string->letters input)
            positions (map letter->pos letters)]
        (when-not (= 2 (count positions))
          ((:error notify) "!!! Please digit exactly 2 letters \n")
          (game-loop board notify))
        (if-let [new-board (move board (first positions) (second positions))]
          (play-next-turn-or-gameover new-board notify)
          (do
            ((:error notify) "\n!!! That was an invalid move :(\n")
            (game-loop board notify)))))))
