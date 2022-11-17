(ns exfn.app
  (:require [reagent.dom :as dom]
            [re-frame.core :as rf]
            [exfn.subscriptions]
            [exfn.events]
            [exfn.logic :as ms]
            [goog.string.format]
            [re-pressed.core :as rp]))




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
      [:div.row.guesses
       [:div.row {:style {:justify-content :center}}
        (for [n (range 1 6)]
          [:div.letter-cell
           {:data-filled (not= "" (get-in rows [1 n]))
            :key (str "1-" n)}
           (get-in rows [1 n])])]
       [:div.row {:style {:justify-content :center}}
        (for [n (range 1 6)]
          [:div.letter-cell
           {:data-filled (not= "" (get-in rows [2 n]))
            :key (str "2-" n)}
           (get-in rows [2 n])])]
       [:div.row {:style {:justify-content :center}}
        (for [n (range 1 6)]
          [:div.letter-cell
           {:data-filled (not= "" (get-in rows [3 n]))
            :key (str "3-" n)}
           (get-in rows [3 n])])]
       [:div.row {:style {:justify-content :center}}
        (for [n (range 1 6)]
          [:div.letter-cell
           {:data-filled (not= "" (get-in rows [4 n]))
            :key (str "4-" n)}
           (get-in rows [4 n])])]
       [:div.row {:style {:justify-content :center}}
        (for [n (range 1 6)]
          [:div.letter-cell
           {:data-filled (not= "" (get-in rows [5 n]))
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
  "window.onkeydown= function(gfg){
        if(gfg.keyCode === space_bar){
            value++;
            demo.innerHTML = value;
        };
        if(gfg.keyCode === right_arrow)
       {
           alert("Welcome to GeeksForGeeks!");
       };
    };"
  
  )