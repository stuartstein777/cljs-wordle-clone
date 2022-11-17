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
