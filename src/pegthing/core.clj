(ns pegthing.core
  "Pegthing game from Clojure for the Brave and True"
  (:gen-class)
  (:require [pegthing.prompt :refer [new-game]]))

(defn -main
  [& _args]
  (println "Get ready to play peg thing!")
  (new-game))
