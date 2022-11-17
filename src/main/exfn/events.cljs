(ns exfn.events
  (:require [re-frame.core :as rf]
            [exfn.logic :as bf]
            [clojure.set :as set]
            [clojure.string :as str]
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

(defn word-in-word-list? [word]
  
  )

(rf/reg-event-db
 :clicked
 (fn [{:keys [guesses current-row current-col] :as db} [_ key]]
   (condp = key
     "DEL" (-> db
               (update :guesses assoc-in [current-row (dec current-col)] "")
               (update :current-col dec-min))
     
     "ENTER" (let [word (->> (get-in guesses [current-row])
                             vals
                             (apply str))]
               (if (w/words (str/lower-case word))
                 (-> (assoc db :current-word word)
                     (assoc :error ""))
                 (-> (assoc db :current-word "")
                     (assoc :error "Invalid word"))))
     
     (-> db
         (update :guesses assoc-in [current-row current-col] key)
         (update :current-col inc-max)))))