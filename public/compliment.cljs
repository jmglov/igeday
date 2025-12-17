(ns igeday.compliment
  (:require [clojure.string :as str]))

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

(defn handle-compliment-button [_ev]
  (->> (get-compliment (or (:compliments @state) compliments))
       (show-compliment! (get-el "#compliment"))))

(defn import-compliments! [_ev]
  (->> (get-el "#compliments")
       .-value
       str/split-lines
       (swap! state assoc :compliments))
  (js/console.log "Loaded" (count (:compliments @state)) "compliments"))

(defn init-ui! []
  (.addEventListener (get-el "#get-compliment") "click"
                     handle-compliment-button)
  (.addEventListener (get-el "#import") "click"
                     import-compliments!)
  (js/console.log "Hello, IGEDay!"))

(when-not (:loaded @state)
  (init-ui!)
  (swap! state assoc :loaded true))

(comment

  (init-ui!)

  )
