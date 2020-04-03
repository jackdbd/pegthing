(ns pegthing.board-test
  (:require [clojure.test :refer [deftest is testing]]
            [pegthing.board :refer [letter->pos new-board row-tri]]))

(deftest letter-to-position-conversion
  (testing "a translates to 1"
    (is (= 1 (letter->pos "a"))))
  (testing "ab translates to 1"
    (is (= 1 (letter->pos "ab"))))
  (testing "throws the expected exception when passed a number"
    (is (thrown? IllegalArgumentException (letter->pos 3)))))

(deftest initial-game-board
  (testing "the hole at position 1 is pegged and connected"
    (let [hole (get (new-board 3) 1)]
      (is (= true (:pegged hole)))
      (is (= {4 2 6 3} (:connections hole))))))

(deftest last-triangular-number-at-nth-row
  (testing "at row 3 is 6"
    (is (= 6 (row-tri 3))))
  (testing "at row 5 is 15"
    (is (= 15 (row-tri 5)))))
