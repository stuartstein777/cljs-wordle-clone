(ns exfn.app
  (:require [reagent.dom :as dom]
            [re-frame.core :as rf]
            [exfn.subscriptions]
            [exfn.events]
            [exfn.logic :as ms]
            [goog.string.format]))




;; -- App -------------------------------------------------------------------------
(defn app []
  (let [word  @(rf/subscribe [:word])
        rows @(rf/subscribe [:guesses])]
    [:div.container
     [:div.game
      [:div.row
       [:div.col.col-lg-8
        [:h1 "Wordle"]]
       [:div.col.col-lg-4
        [:i.fas.fa-cubes.stats]]]
      [:div.row
       [:p
        (str "word: " word)]]
      [:div.row.guesses
       [:div.row {:style {:justify-content :center}}
        [:div.letter-cell (get-in rows [1 1])]
        [:div.letter-cell (get-in rows [1 2])]
        [:div.letter-cell (get-in rows [1 3])]
        [:div.letter-cell (get-in rows [1 4])]
        [:div.letter-cell (get-in rows [1 5])]]
       [:div.row {:style {:justify-content :center}}
        [:div.letter-cell (get-in rows [2 1])]
        [:div.letter-cell (get-in rows [2 2])]
        [:div.letter-cell (get-in rows [2 3])]
        [:div.letter-cell (get-in rows [2 4])]
        [:div.letter-cell (get-in rows [2 5])]]
       [:div.row {:style {:justify-content :center}}
        [:div.letter-cell (get-in rows [3 1])]
        [:div.letter-cell (get-in rows [3 2])]
        [:div.letter-cell (get-in rows [3 3])]
        [:div.letter-cell (get-in rows [3 4])]
        [:div.letter-cell (get-in rows [3 5])]]
       [:div.row {:style {:justify-content :center}}
        [:div.letter-cell (get-in rows [4 1])]
        [:div.letter-cell (get-in rows [4 2])]
        [:div.letter-cell (get-in rows [4 3])]
        [:div.letter-cell (get-in rows [4 4])]
        [:div.letter-cell (get-in rows [4 5])]]
       [:div.row {:style {:justify-content :center}}
        [:div.letter-cell (get-in rows [5 1])]
        [:div.letter-cell (get-in rows [5 2])]
        [:div.letter-cell (get-in rows [5 3])]
        [:div.letter-cell (get-in rows [5 4])]
        [:div.letter-cell (get-in rows [5 5])]]
       [:div.row {:style {:justify-content :center}}
        [:div.letter-cell (get-in rows [6 1])]
        [:div.letter-cell (get-in rows [6 2])]
        [:div.letter-cell (get-in rows [6 3])]
        [:div.letter-cell (get-in rows [6 4])]
        [:div.letter-cell (get-in rows [6 5])]]]
      [:div.keyboard
       [:div.keyboard-row
        [:div.keyboard-key "Q"]
        [:div.keyboard-key "W"]
        [:div.keyboard-key "E"]
        [:div.keyboard-key "R"]
        [:div.keyboard-key "T"]
        [:div.keyboard-key "Y"]
        [:div.keyboard-key "U"]
        [:div.keyboard-key "I"]
        [:div.keyboard-key "O"]
        [:div.keyboard-key "P"]]
       [:div.keyboard-row
        [:div.keyboard-key "A"]
        [:div.keyboard-key "S"]
        [:div.keyboard-key "D"]
        [:div.keyboard-key "F"]
        [:div.keyboard-key "G"]
        [:div.keyboard-key "H"]
        [:div.keyboard-key "J"]
        [:div.keyboard-key "K"]
        [:div.keyboard-key "L"]]
       [:div.keyboard-row
        [:div.keyboard-key "ENTER"]
        [:div.keyboard-key "Z"]
        [:div.keyboard-key "X"]
        [:div.keyboard-key "C"]
        [:div.keyboard-key "V"]
        [:div.keyboard-key "B"]
        [:div.keyboard-key "N"]
        [:div.keyboard-key "M"]
        [:div.keyboard-key "DEL"]]
       ]]]))

;; -- After-Load --------------------------------------------------------------------
;; Do this after the page has loaded.
;; Initialize the initial db state.
(defn ^:dev/after-load start
  []
  (dom/render [app]
              (.getElementById js/document "app")))

(defn ^:export init []
  (start))

; dispatch the event which will create the initial state. 
(defonce initialize (rf/dispatch-sync [:initialize]))

(comment
  
  )