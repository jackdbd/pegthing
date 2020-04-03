(ns pegthing.game-rules-test
  (:require [clojure.test :refer [deftest is testing]]
            [pegthing.board :refer [new-board]]
            [pegthing.game-rules :refer [can-move? pegged? valid-move? valid-moves]]
            [pegthing.player :refer [board-without-peg-at-pos]]))

(deftest pegs-on-the-game-board
  (testing "all holes are pegged when the game starts"
    (let [board (new-board 3)]
      (is (= true (pegged? board 1)))
      (is (= true (pegged? board 2)))
      (is (= true (pegged? board 3)))
      (is (= true (pegged? board 4)))
      (is (= true (pegged? board 5)))
      (is (= true (pegged? board 6)))
      (is (= nil (pegged? board 7))))))

(deftest valid-moves-on-the-game-board
  (testing "there are no valid moves for any position on a new board (before removing one peg)"
    (let [board (new-board 3)]
      (is (= {} (valid-moves board 1)))
      (is (= {} (valid-moves board 2)))
      (is (= {} (valid-moves board 3)))
      (is (= {} (valid-moves board 4)))
      (is (= {} (valid-moves board 5)))
      (is (= {} (valid-moves board 6)))))
  (testing "1 -> 4 is a valid move if hole 2 has no peg"
    (def pos-source 1)
    (def pos-destination 4)
    (def pos-jumped 2)
    (let [board (board-without-peg-at-pos (new-board 3) pos-destination)]
      (is (= {pos-destination pos-jumped} (valid-moves board pos-source))))))

(deftest a-valid-move-returns-the-jumped-position
  (def pos-source 1)
  (def pos-destination 4)
  (def pos-jumped 2)
  (testing "1 -> 4 is valid and returns 2"
    (let [board (board-without-peg-at-pos (new-board 3) pos-destination)]
      (is (= pos-jumped (valid-move? board pos-source pos-destination)))))
  (testing "1 -> 2 is invalid and returns nil"
    (let [board (board-without-peg-at-pos (new-board 3) pos-destination)]
      (is (= nil (valid-move? board pos-source pos-jumped))))))

(deftest can-the-player-move?
  (def initial-board (new-board 3))
  (def pos-destination 4)
  (testing "cannot move on an new board (before having removed one peg)"
    (is (= nil (can-move? initial-board))))
  (testing "can move after a peg is removed"
    (let [board (board-without-peg-at-pos initial-board pos-destination)]
      (is (not= nil (can-move? board))))))
