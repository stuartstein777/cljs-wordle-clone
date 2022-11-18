(ns exfn.app
  (:require [reagent.dom :as dom]
            [re-frame.core :as rf]
            [exfn.subscriptions]
            [clojure.string :as str]
            [exfn.events]
            [exfn.logic :as ms]
            [goog.string.format]
            [re-pressed.core :as rp]))

(defn error-message []
  (let [error @(rf/subscribe [:error])]
    [:div.invalid-word-error
     {:data-error error}
     "Not in word list!"]))

(defn guess-background [col row]
  (let [word        @(rf/subscribe [:word])
        current-row @(rf/subscribe [:current-row])
        guesses     @(rf/subscribe [:guesses])
        row-guess   (guesses row)]
    (if (> current-row row)
      (if (and row-guess ((set word) (row-guess col)))
        (if (= (nth word (dec col)) (row-guess col))
          "#538d4e"
          "#b59f3b")
        "#3a3a3c")
      "#121213")))

(defn guess-row [current-row current-col rows error row-no]
  [:div.row {:style {:justify-content :center}}
   (for [n (range 1 6)]
     [:div.letter-cell
      {:data-filled (and (= n current-col)
                         (= row-no current-row)
                         (not= "" (get-in rows [row-no n])))
       :data-error (and error (= current-row row-no))
       :data-guessed (= 1 (- current-row row-no))
       :style {:background-color (guess-background n row-no)}
       :key (str row-no "-" n)}
      (get-in rows [row-no n])])])

(defn get-key-bg [guessed-letters key]
  (if (guessed-letters key)
    "#3a3a3c"
    "#818384"))

(defn keyboard-row [keys]
  (let [guessed-letters @(rf/subscribe [:guessed-letters])]
    [:div.keyboard-row
     (for [letter (str/split keys #"-")]
       [:div.keyboard-key {:on-click #(rf/dispatch [:clicked letter])
                           :style {:background-color (get-key-bg guessed-letters letter)}
                           :key letter} letter])]))

;; -- App -------------------------------------------------------------------------
(defn app []
  (let [rows  @(rf/subscribe [:guesses])
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
       (for [n (range 1 7)]
         [guess-row current-row current-col rows error n])]
      [:div.keyboard
       [keyboard-row "Q-W-E-R-T-Y-U-I-O-P"]
       [keyboard-row "A-S-D-F-G-H-J-K-L"]
       [keyboard-row "ENTER-Z-X-C-V-B-N-M-DEL"]]]]))

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