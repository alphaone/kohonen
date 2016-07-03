(ns kohonen.ui.main
  (:require [kohonen.ui.chart :as chart]
            [kohonen.ui.timeout :as timeout]
            [kohonen.ui.current-time :as time]))

(defn start-button [start-fn]
  [:div
   [:button {:on-click start-fn} "START!!!"]])

(defn main [start-fn learning-rate-fn]
  [:div
   [chart/chart]
   [start-button start-fn]
   [time/current-time learning-rate-fn]
   [timeout/timeout-input]])