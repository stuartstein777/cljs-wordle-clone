(ns main.exfn.scratch
  (:require [exfn.words :as w]
            [clojure.string :as str]
            [clojure.set :as set]))

(let [guesses {1 10
               2 20
               3 8
               4 4
               5 2
               6 11}
      max-width 10]
  
  (let [max (->> guesses vals (apply max))]
    {1 (int (* max-width (/ (guesses 1) max)))
     2 (int (* max-width (/ (guesses 2) max)))
     3 (int (* max-width (/ (guesses 3) max)))
     4 (int (* max-width (/ (guesses 4) max)))
     5 (int (* max-width (/ (guesses 5) max)))
     6 (int (* max-width (/ (guesses 6) max)))
     }
    )
  )

(defn guess-distribution-histogram [guesses]
  (let [max (->> guesses vals (apply max))]
    {1 (int (* 150 (/ (guesses 1) max)))
     2 (int (* 150 (/ (guesses 2) max)))
     3 (int (* 150 (/ (guesses 3) max)))
     4 (int (* 150 (/ (guesses 4) max)))
     5 (int (* 150 (/ (guesses 5) max)))
     6 (int (* 150 (/ (guesses 6) max)))}))


; 1 #####                 (10 / 20) = 0.5, 0.5 * 10 = 5
; 2 ##########            (20 / 20) = 1.0, 1.0 * 10 = 10
; 3 ####
; 4 ##
; 5 #
; 6 #####