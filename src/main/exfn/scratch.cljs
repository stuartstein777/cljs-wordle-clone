(ns main.exfn.scratch
  (:require [exfn.words :as w]
            [clojure.string :as str]))
(let [db {:word    (-> w/words
                       shuffle
                       first)

          :guesses {1 {1 "A", 2 "B", 3 "C", 4 "D", 5 "E"}
                    2 {1 "", 2 "", 3 "", 4 "", 5 ""}
                    3 {1 "", 2 "", 3 "", 4 "", 5 ""}
                    4 {1 "", 2 "", 3 "", 4 "", 5 ""}
                    5 {1 "", 2 "", 3 "", 4 "", 5 ""}
                    6 {1 "", 2 "", 3 "", 4 "", 5 ""}}

          :guessed-letters #{}
          :current-row 1
          :current-col 1}]
  (->> (get-in (db :guesses) [(db :current-row)])
      vals
      (apply str))  
  )