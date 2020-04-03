(ns pegthing.core
  "Pegthing game from Clojure for the Brave and True"
  (:gen-class)
  (:require [pegthing.prompt :refer [new-game]]))

(defn -main
  []
  (println "Get ready to play peg thing!")
  (new-game))
