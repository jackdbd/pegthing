(ns pegthing.game-rules-test
  (:require [clojure.test :refer [deftest is testing]]
            [pegthing.board :refer [new-board]]
            [pegthing.game-rules :refer [pegged? valid-moves]]))

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
  (testing "there are no valid moves a position 1"
    (let [board (new-board 3)]
      (is (= {} (valid-moves board 1))))))
