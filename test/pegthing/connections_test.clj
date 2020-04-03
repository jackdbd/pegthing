(ns pegthing.connections-test
  (:require [clojure.test :refer [deftest is testing]]
            [pegthing.connections :refer [connect-right pos->row-num]]
            [pegthing.board :refer [new-board]]))

(deftest position-to-row-number-conversion
  (testing "position 3 translates to row number 2"
    (is (= 2 (pos->row-num 3))))
  (testing "position 15 translates to row number 5"
    (is (= 5 (pos->row-num 15)))))

(deftest connect-to-the-right
  (testing "returns the same board if it can't connect"
    (let [board (new-board 3) max-pos 6 pos 2]
      (is (= board (connect-right board max-pos pos))))))
