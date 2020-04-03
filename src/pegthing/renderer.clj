(ns pegthing.renderer
  "Pegthing board renderer for the terminal."
  (:require
   [clojure.spec.alpha :as s]
   [clojure.string :as string]
   [pegthing.board :refer [letters positions-for-letter row-num->positions]]
   [pegthing.colors :refer [colorize]]
   [pegthing.utils :refer [ex-info-helper]]))

(defn- make-pos-renderer
  "Create a renderer for a position `pos` on a given board `board`."
  [board]
  (fn [pos]
    (str (nth letters (dec pos))
         (if (get-in board [pos :pegged])
           (colorize "0" :blue)
           (colorize "-" :red)))))

(defn- make-padding-fn
  [num-rows]
  (fn [row-num]
    (let [pad-length (/ (* (- num-rows row-num) positions-for-letter) 2)]
      (apply str (take pad-length (repeat " "))))))

(defn- make-row-renderer
  "Create a renderer for a single row on a given board `board`."
  [board]
  (let [num-rows (:pegthing.board/num-rows board)
        row-padding-fn (make-padding-fn num-rows)
        pos-renderer (make-pos-renderer board)]
    (fn [row-num]
      (let [positions (row-num->positions row-num)]
        (str (row-padding-fn row-num)
             (string/join " " (map pos-renderer positions)))))))

(defn print-board
  "Print the board `board` in the terminal."
  [board]
  (if-not (s/valid? :pegthing.board/board board)
    (throw (ex-info-helper :pegthing.board/board board))
    (let [row-renderer (make-row-renderer board)]
      (doseq [row-num (range 1 (inc (:pegthing.board/num-rows board)))]
        (println (row-renderer row-num))))))
