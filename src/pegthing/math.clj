(ns pegthing.math)

(defn tri*
  "Generate a lazy sequence of triangular numbers.
  [Triangular Numbers](https://www.mathsisfun.com/algebra/triangular-numbers.html)"
  ([] (tri* 0 1))
  ([sum n]
   (let [new-sum (+ sum n)]
     (cons new-sum (lazy-seq (tri* new-sum (inc n)))))))

(def tri (tri*))

(defn triangular?
  "Is the number triangular? e.g. 1, 3, 6, 10, 15, etc"
  [n]
  (= n (last (take-while #(>= n %) tri))))