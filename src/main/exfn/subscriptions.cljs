(ns exfn.subscriptions
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :game-won?
 (fn [db _] (db :game-won?)))