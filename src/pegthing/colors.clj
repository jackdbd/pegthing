(ns pegthing.colors)

(def ansi-styles
  {:red   "[31m"
   :green "[32m"
   :blue  "[34m"
   :reset "[0m"})

(defn ansi
  "Produce a string which will apply an ansi style."
  [style]
  (str \u001b (style ansi-styles)))

(defn colorize
  "Apply ansi color to text."
  [text color]
  (str (ansi color) text (ansi :reset)))
