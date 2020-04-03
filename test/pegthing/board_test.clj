(ns pegthing.board-test
  (:require [clojure.test :refer [deftest is testing]]
            [pegthing.board :as board]))

(deftest letter-to-position-conversion
  (testing "a translates to 1"
    (is (= 1 (board/letter->pos "a"))))
  (testing "ab translates to 1"
    (is (= 1 (board/letter->pos "ab"))))
  (testing "throws the expected exception when passed a number"
    (is (thrown? IllegalArgumentException (board/letter->pos 3)))))

(deftest initial-game-board
  (testing "the hole at position 1 is pegged and connected"
    (let [hole (get (board/new-board 3) 1)]
      (is (= true (:pegged hole)))
      (is (= {4 2, 6 3} (:connections hole))))))

(deftest positions-at-nth-row
  (testing "at row 3 the positions available are: 4, 5, 6"
    (is (= (seq [4 5 6]) (board/row-num->positions 3)))))

(deftest pegs-on-the-game-board
  (testing "all holes are pegged when the game starts"
    (let [board (board/new-board 3)]
      (is (= true (board/pegged? board 1)))
      (is (= true (board/pegged? board 2)))
      (is (= true (board/pegged? board 3)))
      (is (= true (board/pegged? board 4)))
      (is (= true (board/pegged? board 5)))
      (is (= true (board/pegged? board 6)))
      (is (= nil (board/pegged? board 7))))))

(deftest remove-peg-from-board
  (testing "remove a peg at position 1"
    (let [pos 1
          board-before-move (board/new-board 3)
          board-after-move (board/board-without-peg-at-pos board-before-move pos)]
      (is (= true (:pegged (get board-before-move pos))))
      (is (= false (:pegged (get board-after-move pos)))))))

(deftest place-peg-on-board
  (testing "place a peg at position 1"
    (let [pos 1
          board-before-move (board/board-without-peg-at-pos (board/new-board 3) pos)
          board-after-move (board/board-with-new-peg board-before-move pos)]
      (is (= false (:pegged (get board-before-move pos))))
      (is (= true (:pegged (get board-after-move pos)))))))

(deftest add-position-on-board
  (testing "place a peg at position 1"
    (let [pos 1
          max-pos 6
          board-before {:rows 3}
          board-after (board/board-with-new-pos board-before max-pos pos)]
      (is (= board-after {:rows 3
                          1 {:pegged true, :connections {4 2, 6 3}}
                          4 {:connections {1 2}}
                          6 {:connections {1 3}}})))))
