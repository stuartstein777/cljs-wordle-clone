(ns exfn.logic
  (:require [clojure.set :as set]))

(defn guess-distribution-histogram [guesses]
  (let [max (->> guesses vals (apply max))]
    {1 (int (* 250 (/ (guesses 1) max)))
     2 (int (* 250 (/ (guesses 2) max)))
     3 (int (* 250 (/ (guesses 3) max)))
     4 (int (* 250 (/ (guesses 4) max)))
     5 (int (* 250 (/ (guesses 5) max)))
     6 (int (* 250 (/ (guesses 6) max)))}))

(defn generate-cookie [stats-and-guesses]
  (str "guess-1=" (apply str (vals ((->> stats-and-guesses :guesses) 1))) ";"
       "guess-2=" (apply str (vals ((->> stats-and-guesses :guesses) 2))) ";"
       "guess-3=" (apply str (vals ((->> stats-and-guesses :guesses) 3))) ";"
       "guess-4=" (apply str (vals ((->> stats-and-guesses :guesses) 4))) ";"
       "guess-5=" (apply str (vals ((->> stats-and-guesses :guesses) 5))) ";"
       "guess-6=" (apply str (vals ((->> stats-and-guesses :guesses) 6))) ";"
       "current-streak=" (-> stats-and-guesses :stats :current-streak)
       "max-streak=" (-> stats-and-guesses :stats :max-streak)
       "wins=" (-> stats-and-guesses :stats :wins)
       "played=" (-> stats-and-guesses :stats :wins)
       "solves=" (str/join "," (-> stats-and-guesses :stats :solves vals))
       "expires=Fri, 31 Dec 2100 12:00:00 UTC; path=/"))