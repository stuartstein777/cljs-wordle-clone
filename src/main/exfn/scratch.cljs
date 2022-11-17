(ns main.exfn.scratch
  (:require [exfn.words :as w]
            [clojure.string :as str]))

(let [word "SPADE"
      guesses {1 "E", 2 "R", 3 "A", 4 "S", 5 "E"}
      col 2]
  (if ((set word) (guesses (inc col)))
    (if (= (nth word col) (guesses (inc col)))
     :green
     :yellow)
    :grey))

