(ns exfn.logic
  (:require [clojure.set :as set]
            [clojure.string :as str]
            [cljs.reader :as rdr]))

(defn guess-distribution-histogram [guesses]
  (let [max (->> guesses vals (apply max))]
    {1 (int (* 250 (/ (guesses 1) max)))
     2 (int (* 250 (/ (guesses 2) max)))
     3 (int (* 250 (/ (guesses 3) max)))
     4 (int (* 250 (/ (guesses 4) max)))
     5 (int (* 250 (/ (guesses 5) max)))
     6 (int (* 250 (/ (guesses 6) max)))}))
