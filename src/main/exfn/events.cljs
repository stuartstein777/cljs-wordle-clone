(ns exfn.events
  (:require [re-frame.core :as rf]
            [exfn.logic :as bf]
            [clojure.set :as set]
            [clojure.string :as str]
            [exfn.words :as w]
            [cljs.reader :as rdr]))

(def reset-guesses
  {1 {1 "", 2 "", 3 "", 4 "", 5 ""}
   2 {1 "", 2 "", 3 "", 4 "", 5 ""}
   3 {1 "", 2 "", 3 "", 4 "", 5 ""}
   4 {1 "", 2 "", 3 "", 4 "", 5 ""}
   5 {1 "", 2 "", 3 "", 4 "", 5 ""}
   6 {1 "", 2 "", 3 "", 4 "", 5 ""}})

(defn get-word []
  (-> w/words
      shuffle
      first
      str/upper-case))

(rf/reg-fx
 :save-to-cookie
 (fn [db]
   (set! (.-cookie js/document)
         (pr-str db))))

(rf/reg-event-db
 :initialize
 (fn [_ _]
   (let [cookie (rdr/read-string (.-cookie js/document))]
     (if (or (= cookie {}) (nil? cookie))
       {:word    (get-word)
        :guesses reset-guesses
        :guessed-letters #{}
        :correct-letters {:green  #{}
                          :yellow #{}}
        :current-row 1
        :current-col 0
        :error false
        :stats {:current-streak 0
                :max-streak 0
                :wins 0
                :solves {1 0, 2 0, 3 0, 4 0, 5 0, 6 0}
                :played 0}
        :stats-visible false
        :game-state :playing}
       cookie))))

(rf/reg-event-db 
 :new-game
 (fn [{:keys [stats]} _]
   {:stats stats
    :word (get-word)
    :guesses reset-guesses
    :guessed-letters #{}
    :correct-letters {:green  #{}
                      :yellow #{}}
    :stats-visible false
    :current-row 1
    :current-col 0
    :error false
    :game-state :playing}))

(defn clamp [n]
  (min (max n 0) 5))

(defn valid-key? [key]
  ((set "ABCDEFGHJIKLMNOPQRSTUVWXYZ") key))

(defn get-correct-letters [word guess]
  (let [green (->> (map (fn [w g] (if (= w g) g nil)) word guess)
                   (remove nil?)
                   set)

        yellow (set/difference (set (filter (fn [g] ((set word) g)) guess)) green)]
    {:green green
     :yellow yellow}))

(defn set-game-over [{:keys [word current-row] :as db} guess]
  (cond 
    (= word guess)
    (-> db
        (assoc :game-state :won)
        (update-in [:stats :current-streak] inc)
        (update-in [:stats :max-streak]
                   (fn [cs] (max cs (inc (-> db :stats :current-streak)))))
        (update-in [:stats :solves (dec current-row)] inc)
        (update-in [:stats :played] inc)
        (update-in [:stats :wins] inc))
    
    (= current-row 7)
    (-> db
        (assoc :game-state :lost)
        (assoc-in [:stats :current-streak] 0)
        (update-in [:stats :played] inc))
    
    :else
    (assoc db :game-state :playing)))

(defn process-key
  [{:keys [guesses current-row current-col :guessed-letters game-state word] :as db} key]
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
                      (assoc :correct-letters (get-correct-letters word guess))
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

(rf/reg-event-fx
 :key-pressed
 (fn [{:keys [db]} [_ key]]
   (let [updated-db (process-key db ({13 "ENTER", 8 "DEL"} key (char key)))]
     {:db updated-db
      :save-to-cookie updated-db})))

(rf/reg-event-db
 :toggle-stats
 (fn [db _]
   (update db :stats-visible not)))
