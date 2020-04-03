(ns pegthing.player-test
  (:require [clojure.test :refer [deftest is testing]]
            [pegthing.board :refer [new-board]]
            [pegthing.player :refer [place-peg remove-peg]]))

(deftest remove-peg-from-board
  (testing "remove a peg at position 1"
    (def pos 1)
    (def board-before-move (new-board 3))
    (let [board-after-move (remove-peg board-before-move pos)]
      (is (= true (:pegged (get board-before-move pos))))
      (is (= false (:pegged (get board-after-move pos)))))))

(deftest place-peg-on-board
  (testing "place a peg at position 1"
    (def pos 1)
    (def board-before-move (remove-peg (new-board 3) pos))
    (let [board-after-move (place-peg board-before-move pos)]
      (is (= false (:pegged (get board-before-move pos))))
      (is (= true (:pegged (get board-after-move pos)))))))
