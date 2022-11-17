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
    
    :guesses {1 {1 "", 2 "", 3 "", 4 "", 5 ""}
              2 {1 "", 2 "", 3 "", 4 "", 5 ""}
              3 {1 "", 2 "", 3 "", 4 "", 5 ""}
              4 {1 "", 2 "", 3 "", 4 "", 5 ""}
              5 {1 "", 2 "", 3 "", 4 "", 5 ""}
              6 {1 "", 2 "", 3 "", 4 "", 5 ""}}
    
    :guessed-letters #{}
    :current-row 1
    :current-col 1}))

(defn inc-max [n]
  (min (inc n) 6))

(defn dec-min [n]
  (max (dec n) 1))

(rf/reg-event-db
 :clicked
 (fn [db [_ key]]
   (condp = key
     "DEL" (-> db
               (update :guesses assoc-in [(db :current-row) (dec (db :current-col))] "")
               (update :current-col dec-min))
     
     "ENTER" (-> db)
     
     (-> db
         (update :guesses assoc-in [(db :current-row) (db :current-col)] key)
         (update :current-col inc-max))
     
     )))