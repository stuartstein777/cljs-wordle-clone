(ns exfn.events
  (:require [re-frame.core :as rf]
            [exfn.logic :as bf]
            [clojure.set :as set]
            [exfn.words :as w]))

(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:word    (-> w/words
                 shuffle
                 first)
    
    :guesses {1 {1 "E", 2 "A", 3 "R", 4 "T", 5 "H"}
              2 {1 "", 2 "", 3 "", 4 "", 5 ""}
              3 {1 "", 2 "", 3 "", 4 "", 5 ""}
              4 {1 "", 2 "", 3 "", 4 "", 5 ""}
              5 {1 "", 2 "", 3 "", 4 "", 5 ""}
              6 {1 "", 2 "", 3 "", 4 "", 5 ""}}
    
    :guessed-letters #{}
    }))

