(ns igeday.compliment)

(defonce state (atom {}))

(def compliments
  ["You're amazing!"
   "You're so smart!"
   "You're the best!"
   "You're nice!"])

(defn get-el [selector]
  (if (string? selector)
    (js/document.querySelector selector)
    selector))

(defn show-compliment! [el compliment]
  (set! (.-innerText el) compliment))

(defn get-compliment [compliments-list]
  (->> (shuffle compliments-list) first))

(defn handle-compliment-button [ev]
  (->> (get-compliment compliments)
       (show-compliment! (get-el "#compliment"))))

(defn init-ui! []
  (.addEventListener (get-el "#get-compliment") "click"
                     handle-compliment-button)
  (js/console.log "Hello, IGEDay!"))

(when-not (:loaded @state)
  (init-ui!)
  (swap! state assoc :loaded true))

(comment

  (init-ui!)

  )
