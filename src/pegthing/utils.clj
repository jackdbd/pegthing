(ns pegthing.utils
  (:require
   [clojure.spec.alpha :as s]
   [clojure.string :as string]))

(s/def ::default-input string?)
(s/def ::non-empty-string (s/and string? not-empty))

(defn ex-info-helper
  "Wrapper for `ex-info`, to keep exceptions' shape consistent."
  [spec x]
  (ex-info (s/explain-str spec x)
           (s/explain-data spec x)))

(defn string->letters
  "Convert a string to a collection consisting of each individual character."
  [string]
  (when-not (s/valid? ::non-empty-string string)
    (ex-info-helper ::non-empty-string string))
  (re-seq #"[a-zA-Z]" string))

(defn get-input
  "Wait for the player to enter text and hit enter, then clean the input."
  [default]
  (if-not (s/valid? ::default-input default)
    (throw (ex-info-helper ::default-input default))
    (let [input (string/trim (read-line))]
      (if (empty? input)
        default
        (string/lower-case input)))))
