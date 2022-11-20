(ns main.exfn.scratch
  (:require [exfn.words :as w]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn write-cookie [stats-and-guesses]
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
       "solves=" (str/join "," (-> stats-and-guesses :stats :solves vals))))

(write-cookie
 {:guesses {1 {1 "S", 2 "H", 3 "O", 4 "R", 5 "E"}
            2 {1 "W", 2 "O", 3 "M", 4 "A", 5 "N"}
            3 {1 "", 2 "", 3 "", 4 "", 5 ""}
            4 {1 "", 2 "", 3 "", 4 "", 5 ""}
            5 {1 "", 2 "", 3 "", 4 "", 5 ""}
            6 {1 "", 2 "", 3 "", 4 "", 5 ""}}
  :stats {:current-streak 0
          :max-streak 0
          :wins 0
          :solves {1 10, 2 2, 3 1, 4 0, 5 0, 6 0}
          :played 0}})

"guess-1=SHORE;guess-2=WOMAN;guess-3=;guess-4=;guess-5=;guess-6=;current-streak=0max-streak=0wins=0played=0solves=10,2,1,0,0,0"
