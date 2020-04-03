(ns pegthing.core
  (:require [pegthing.ui :refer [prompt-rows]])
  (:gen-class))

(defn -main
  [& args]
  (println "Get ready to play peg thing!")
  (prompt-rows))
