(ns pegthing.player-test
  (:require [clojure.test :refer [deftest is testing]]
            [pegthing.board :refer [new-board]]
            [pegthing.player :refer [board-with-new-pos board-with-new-peg board-without-peg-at-pos]]))

(deftest remove-peg-from-board
  (testing "remove a peg at position 1"
    (def pos 1)
    (def board-before-move (new-board 3))
    (let [board-after-move (board-without-peg-at-pos board-before-move pos)]
      (is (= true (:pegged (get board-before-move pos))))
      (is (= false (:pegged (get board-after-move pos)))))))

(deftest place-peg-on-board
  (testing "place a peg at position 1"
    (def pos 1)
    (def board-before-move (board-without-peg-at-pos (new-board 3) pos))
    (let [board-after-move (board-with-new-peg board-before-move pos)]
      (is (= false (:pegged (get board-before-move pos))))
      (is (= true (:pegged (get board-after-move pos)))))))

(deftest add-position-on-board
  (testing "place a peg at position 1"
    (def pos 1)
    (def max-pos 6)
    (def board-before {:rows 3})
    (def board-after (board-with-new-pos board-before max-pos pos))
    (is (= board-after {:rows 3,
                        1 {:pegged true, :connections {4 2, 6 3}}, 
                        4 {:connections {1 2}}, 
                        6 {:connections {1 3}}}))))
