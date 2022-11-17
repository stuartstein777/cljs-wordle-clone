(ns exfn.app
  (:require [reagent.dom :as dom]
            [re-frame.core :as rf]
            [exfn.subscriptions]
            [exfn.events]
            [exfn.logic :as ms]
            [goog.string.format]
            [re-pressed.core :as rp]))

(defn error-message []
  (let [error @(rf/subscribe [:error])]
    [:div.invalid-word-error
     {:data-error error}
     "Not in word list!"]))


;; -- App -------------------------------------------------------------------------
(defn app []
  (let [word  @(rf/subscribe [:word])
        rows  @(rf/subscribe [:guesses])
        error @(rf/subscribe [:error])
        current-row @(rf/subscribe [:current-row])
        current-col @(rf/subscribe [:current-col])]
    [:div.container
     [:div.game
      [error-message]
      [:div.row
       [:div.col.col-lg-8
        [:h1 "Wordle"]]
       [:div.col.col-lg-4
        [:i.fas.fa-cubes.stats]]]
      [:div.row.guesses
       [:div.row {:style {:justify-content :center}}
        (for [n (range 1 6)]
          [:div.letter-cell
           {:data-filled (and (= n current-col) (not= "" (get-in rows [1 n])))
            :data-error (and error (= current-row 1))
            :key (str "1-" n)}
           (get-in rows [1 n])])]
       [:div.row {:style {:justify-content :center}}
        (for [n (range 1 6)]
          [:div.letter-cell
           {:data-filled (not= "" (get-in rows [2 n]))
            :data-error (and error (= current-row 2))
            :key (str "2-" n)}
           (get-in rows [2 n])])]
       [:div.row {:style {:justify-content :center}}
        (for [n (range 1 6)]
          [:div.letter-cell
           {:data-filled (not= "" (get-in rows [3 n]))
            :data-error (and error (= current-row 3))
            :key (str "3-" n)}
           (get-in rows [3 n])])]
       [:div.row {:style {:justify-content :center}}
        (for [n (range 1 6)]
          [:div.letter-cell
           {:data-filled (not= "" (get-in rows [4 n]))
            :data-error (and error (= current-row 4))
            :key (str "4-" n)}
           (get-in rows [4 n])])]
       [:div.row {:style {:justify-content :center}}
        (for [n (range 1 6)]
          [:div.letter-cell
           {:data-filled (not= "" (get-in rows [5 n]))
            :data-error (and error (= current-row 5))
            :key (str "5-" n)}
           (get-in rows [5 n])])]
       [:div.row {:style {:justify-content :center}}
        (for [n (range 1 6)]
          [:div.letter-cell
           {:data-filled (not= "" (get-in rows [6 n]))
            :key (str "6-" n)}
           (get-in rows [6 n])])]]
      [:div.keyboard
       [:div.keyboard-row
        (for [letter "QWERTYUIOP"]
          [:div.keyboard-key {:on-click #(rf/dispatch [:clicked letter])
                              :key letter} letter])]
       [:div.keyboard-row
        (for [letter "ASDFGHJKL"]
          [:div.keyboard-key {:on-click #(rf/dispatch [:clicked letter])
                              :key letter} letter])]
       [:div.keyboard-row
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "ENTER"])} "ENTER"]
        (for [letter "ZXCVBNM"]
          [:div.keyboard-key {:on-click #(rf/dispatch [:clicked letter])
                              :key letter} letter])
        [:div.keyboard-key {:on-click #(rf/dispatch [:clicked "DEL"])} "DEL"]]]]]))

;; -- After-Load --------------------------------------------------------------------
;; Do this after the page has loaded.
;; Initialize the initial db state.
(defn ^:dev/after-load start
  []
  (rf/clear-subscription-cache!)
  (dom/render [app]
              (.getElementById js/document "app")))

(defn ^:export init []
  (set! (.-onkeydown js/window)
        (fn [gfg]
          (rf/dispatch-sync [:key-pressed (.-keyCode gfg)])))
  #_(rf/dispatch-sync [::rp/add-keyboard-event-listener "keypress" :clear-on-success-event-match])
  
  (start))

; dispatch the event which will create the initial state. 
(defonce initialize (rf/dispatch-sync [:initialize]))
#_(defonce set-up-keypress (rf/dispatch-sync [::rp/add-keyboard-event-listener "keypress" :clear-on-success-event-match]))




(comment
  {a 65 
   q 81
   W 87
   E 69}

  (char 81)
  )