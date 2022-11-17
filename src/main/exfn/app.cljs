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
        rows  @(rf/subscribe [:guesses])]
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
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "Q"])} "Q"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "W"])} "W"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "E"])} "E"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "R"])} "R"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "T"])} "T"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "Y"])} "Y"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "U"])} "U"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "I"])} "I"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "O"])} "O"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "P"])} "P"]]
       [:div.keyboard-row
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "A"])} "A"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "S"])} "S"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "D"])} "D"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "F"])} "F"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "G"])} "G"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "H"])} "H"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "J"])} "J"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "K"])} "K"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "L"])} "L"]]
       [:div.keyboard-row
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "ENTER"])} "ENTER"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "Z"])} "Z"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "X"])} "X"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "C"])} "C"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "V"])} "V"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "B"])} "B"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "N"])} "N"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "M"])} "M"]
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "DEL"])} "DEL"]]]]]))

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