(ns exfn.subscriptions
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :word
 (fn [db _] (db :word)))

(rf/reg-sub
 :guesses
 (fn [db _] (db :guesses)))
