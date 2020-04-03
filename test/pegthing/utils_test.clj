(ns pegthing.utils-test
  (:require [clojure.test :refer [deftest is testing]]
            [pegthing.utils :refer [string->letters]]))

(deftest string-to-letters-conversion
  (testing "ab translates to 'a' 'b'"
    (is (= (seq ["a" "b"]) (string->letters "ab")))))
