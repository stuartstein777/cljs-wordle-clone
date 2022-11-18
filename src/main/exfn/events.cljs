(ns exfn.events
  (:require [re-frame.core :as rf]
            [exfn.logic :as bf]
            [clojure.set :as set]
            [clojure.string :as str]
            [exfn.words :as w]
            [re-pressed.core :as rp]))

(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:word    (-> w/words
                 shuffle
                 first
                 str/upper-case)
    
    :guesses {1 {1 "", 2 "", 3 "", 4 "", 5 ""}
              2 {1 "", 2 "", 3 "", 4 "", 5 ""}
              3 {1 "", 2 "", 3 "", 4 "", 5 ""}
              4 {1 "", 2 "", 3 "", 4 "", 5 ""}
              5 {1 "", 2 "", 3 "", 4 "", 5 ""}
              6 {1 "", 2 "", 3 "", 4 "", 5 ""}}
    
    :guessed-letters #{}
    :current-row 1
    :current-col 0
    :error false}))

(defn clamp [n]
  (min (max n 0) 5))

(defn valid-key? [key]
  ((set "ABCDEFGHJIKLMNOPQRSTUVWXYZ") key))

(defn process-key
  [{:keys [guesses current-row current-col :guessed-letters] :as db} key]
  (condp = key
    "DEL" (if (>= current-col 1)
            (-> db
                (update :guesses assoc-in [current-row current-col] "")
                (update :current-col #(clamp (dec %)))
                (assoc :error false))
            db)

    "ENTER" (if (= 5 current-col)
              (let [word (->> (get-in guesses [current-row])
                              vals
                              (apply str))]
                (if (w/words (str/lower-case word))
                  (-> db
                      (assoc :error false)
                      (update :current-row inc)
                      (assoc :guessed-letters (set/union guessed-letters (set word)))
                      (assoc :current-col 0))
                  (assoc db :error true)))
              db)

    (if (and (valid-key? key) (not= current-col 5))
      (-> db
          (update :guesses assoc-in [current-row (inc current-col)] key)
          (update :current-col #(clamp (inc %)))
          (assoc :error false))
      db)))

(rf/reg-event-db
 :clicked
 (fn [db [_ key]]
   (process-key db key)))

(rf/reg-event-db
 :key-pressed
 (fn [db [_ key]]
   (process-key db ({13 "ENTER", 8 "DEL"} key (char key)))))