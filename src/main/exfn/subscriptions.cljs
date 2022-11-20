(ns exfn.subscriptions
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :word
 (fn [db _] (db :word)))

(rf/reg-sub
 :guesses
 (fn [db _] (db :guesses)))

(rf/reg-sub
 :error
 (fn [db _] (db :error)))

(rf/reg-sub
 :current-row
 (fn [db _] (db :current-row)))

(rf/reg-sub
 :current-col
 (fn [db _] (db :current-col)))

(rf/reg-sub
 :guessed-letters
 (fn [db _] (db :guessed-letters)))

(rf/reg-sub
 :game-state
 (fn [db _] (db :game-state)))

(rf/reg-sub
 :correct-letters
 (fn [db _] (db :correct-letters)))

(rf/reg-sub
 :stats
 (fn [db _] (db :stats)))

(rf/reg-sub
 :stats-visible
 (fn [db _] (db :stats-visible)))