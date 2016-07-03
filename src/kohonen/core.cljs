(ns kohonen.core
  (:require [reagent.core :as r]
            [kohonen.ui.main :as ui]
            [kohonen.ui.chart :as chart-ui]
            [kohonen.ui.timeout :as timeout-ui]
            [kohonen.ui.current-time :as time-ui]
            [kohonen.kohonen :as k]))

(enable-console-print!)

(defonce to (atom nil))

(defn random-point []
  {:x (rand-int 800)
   :y (rand-int 400)})

(defn random-stimulus []
  (case (rand-int 4)
    0 {:x (rand-int 800)
       :y (rand-int 50)}
    1 {:x (rand-int 800)
       :y (+ 350 (rand-int 50))}
    2 {:x (rand-int 100)
       :y (rand-int 400)}
    3 {:x (+ 700 (rand-int 100))
       :y (rand-int 400)})
  )

(defonce starting-neurons
         (repeatedly 30 random-point))

(defn learning-rate-fn [t]
  (/ 1 (Math/pow t 0.2)))

(defn train [neurons t]
  (let [stimulus (random-stimulus)]
    (time-ui/set-current-time t)
    (chart-ui/set-stimulus stimulus)
    (chart-ui/set-nearest (second (k/nearest stimulus neurons)))
    (chart-ui/set-points neurons)

    (let [neurons' (k/apply-stimulus stimulus neurons t learning-rate-fn)]
      (reset! to (js/setTimeout (partial train neurons' (inc t)) @timeout-ui/timeout)))
    ))

(defn start-fn []
  (js/clearTimeout @to)
  (train starting-neurons 1))

(defn by-id [id]
  (.getElementById js/document id))

(r/render [ui/main start-fn learning-rate-fn] (by-id "app"))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
