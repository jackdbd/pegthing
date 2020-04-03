(ns pegthing.math
  "Mathematical functions for triangular numbers.")

(defn tri*
  "Generate an infinite lazy sequence of triangular numbers.
  [Triangular Numbers](https://www.mathsisfun.com/algebra/triangular-numbers.html)"
  ([] (tri* 0 1))
  ([sum n]
   (let [new-sum (+ sum n)]
     (cons new-sum (lazy-seq (tri* new-sum (inc n)))))))

(defn triangular?
  "Is the number triangular? e.g. 1, 3, 6, 10, 15, etc"
  [n]
  (comment
    (triangular? 6) ; (1 3 6) last=6 6=6 => 6 is a triangular number
    (triangular? 7)) ; (1 3 6) last=6 7!=6 => 7 is not a triangular number
  (= n (last (take-while #(>= n %) (tri*)))))

(defn nth-tri
  "The nth triangular number (i.e. the one at the end of nth row)."
  [n]
  (last (take n (tri*))))
