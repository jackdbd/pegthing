(ns pegthing.game-rules-test
  (:require [clojure.test :refer [deftest is testing]]
            [pegthing.board :refer [board-without-peg-at-pos new-board]]
            [pegthing.game-rules :refer [can-move? valid-move? valid-moves]]))

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
    (let [pos-src 1
          pos-dest 4
          pos-jumped 2
          board (board-without-peg-at-pos (new-board 3) pos-dest)]
      (is (= {pos-dest pos-jumped} (valid-moves board pos-src))))))

(deftest a-valid-move-returns-the-jumped-position
  (let [pos-src 1
        pos-dest 4
        pos-jumped 2
        board (new-board 3)]
    (testing "1 -> 4 is valid and returns 2"
      (let [b (board-without-peg-at-pos board pos-dest)]
        (is (= pos-jumped (valid-move? b pos-src pos-dest)))))
    (testing "1 -> 2 is invalid and returns nil"
      (let [b (board-without-peg-at-pos board pos-dest)]
        (is (= nil (valid-move? b pos-src pos-jumped)))))))

(deftest can-the-player-move?
  (let [pos-dest 1 board (new-board 3)]
    (testing "cannot move on an new board (before having removed one peg)"
      (is (= nil (can-move? board))))
    (testing "can move after a peg is removed"
      (let [board (board-without-peg-at-pos board pos-dest)]
        (is (not= nil (can-move? board)))))))
