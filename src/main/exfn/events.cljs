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
    :error false
    :game-state :playing}))

(defn clamp [n]
  (min (max n 0) 5))

(defn valid-key? [key]
  ((set "ABCDEFGHJIKLMNOPQRSTUVWXYZ") key))

(defn set-game-over [{:keys [word current-row] :as db} guess]
  (cond 
    (= word guess)
    (assoc db :game-state :won)
    
    (= current-row 7)
    (assoc db :game-state :lost)
    
    :else
    (assoc db :game-state :playing)))

(defn process-key
  [{:keys [guesses current-row current-col :guessed-letters game-state] :as db} key]
  (condp = key
    "DEL" (if (>= current-col 1)
            (-> db
                (update :guesses assoc-in [current-row current-col] "")
                (update :current-col #(clamp (dec %)))
                (assoc :error false))
            db)

    "ENTER" (if (= 5 current-col)
              (let [guess (->> (get-in guesses [current-row])
                               vals
                               (apply str))]
                (if (w/words (str/lower-case guess))
                  (-> db
                      (assoc :error false)
                      (update :current-row inc)
                      (assoc :guessed-letters (set/union guessed-letters (set guess)))
                      (assoc :current-col 0)
                      (set-game-over guess))
                  (assoc db :error true)))
              db)

    (if (and (= game-state :playing) (valid-key? key) (not= current-col 5))
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