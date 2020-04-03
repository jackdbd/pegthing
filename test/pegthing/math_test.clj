(ns pegthing.math-test
  (:require [clojure.test :refer [deftest is testing]]
            [pegthing.math :refer [tri* triangular?]]))

(deftest triangular-numbers
  (testing "3 is a triangular number"
    (is (= true (triangular? 3))))
  (testing "4 is not a triangular number"
    (is (= false (triangular? 4))))
  (testing "throws the expected exception when passed a string"
    (is (thrown? ClassCastException (triangular? "some-string")))))

(deftest lazy-sequence-of-triangular-number
  (testing "the first tringular number is 1"
    (is (= 1 (first (tri*)))))
  (testing "the fifth tringular number is 15"
    (is (= 15 (last (take 5 (tri*)))))))
