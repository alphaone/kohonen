(ns kohonen.core
  (:require [reagent.core :as r]
            [re-frame.core :refer [dispatch dispatch-sync]]
            [kohonen.handlers]
            [kohonen.subs]
            [kohonen.ui.main :as ui]
            [kohonen.kohonen :as k]
            [re-frame.core :as rf]))

(enable-console-print!)

(defonce current-timeout (atom nil))

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

(defn set-ui-state [stimulus neurons]
  (rf/dispatch [:set-stimulus stimulus])
  (rf/dispatch [:set-neurons neurons])
  (rf/dispatch [:set-nearest (second (k/nearest stimulus neurons))]))

(defn train []
  (let [stimulus (random-stimulus)
        db @re-frame.db/app-db
        neurons' (k/apply-stimulus stimulus (:neurons db) (:t db) learning-rate-fn)]
    (rf/dispatch [:inc-time])
    (set-ui-state stimulus neurons')
    (->> (js/setTimeout train (:timeout db))
         (reset! current-timeout))))

(defn start-fn []
  (js/clearTimeout @current-timeout)
  (rf/dispatch-sync [:set-neurons starting-neurons])
  (train))

(defn by-id [id]
  (.getElementById js/document id))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )

(defn ^:export run
  []
  (dispatch-sync [:initialize-db])
  (r/render [ui/main start-fn learning-rate-fn] (by-id "app")))
