(ns igeday.mood
  (:require [clojure.string :as str]))

(defonce state (atom {:counters {}}))

(def moods {:happy {:color "yellow"}
            :tired {:color "dimgrey"}
            :excited {:color "magenta"}
            :chill {:color "deepskyblue"}})

(def color-transition-ms 250)

(defn get-el [selector]
  (if (string? selector)
    (js/document.querySelector selector)
    selector))

(defn set-body-color! [color]
  (set! (.-style (get-el "body")) (str "background-color: " color)))

(defn count-mood! [mood]
  (swap! state update-in [:counters mood] #(inc (or % 0)))
  (let [sel (get-el (str "#counter-" (name mood)))
        el (get-el sel)
        new-count (get-in @state [:counters mood])]
    (js/console.log "Updating" (name mood) "counter to" new-count el)
    (set-body-color! (get-in moods [mood :color]))
    (set! (.-innerText el) new-count)
    (js/window.setTimeout #(set-body-color! "transparent") color-transition-ms)))

(defn add-listeners! []
  (doseq [mood (keys moods)
          :let [sel (str "#button-" (name mood))]]
    (.addEventListener (get-el sel) "click"
                       (fn [_ev]
                         (js/console.log "Button clicked:" sel)
                         (count-mood! mood)))))

(defn init-ui! []
  (add-listeners!)
  (js/console.log "Hello, future engineers!"))

(when-not (:loaded @state)
  (init-ui!)
  (swap! state assoc :loaded true))

(comment

  (init-ui!)

  )
