(ns pegthing.ui-test
  (:require [clojure.test :refer [deftest is testing]]
            [pegthing.ui :refer [string->letters]]))

(deftest string-to-letters-conversion
  (testing "ab translates to 'a' 'b'"
    (is (= (seq ["a" "b"]) (string->letters "ab")))))
