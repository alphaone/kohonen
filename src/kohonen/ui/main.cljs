(ns kohonen.ui.main
  (:require [kohonen.ui.chart :as chart]
            [kohonen.ui.time :as time]
            [re-frame.core :as rf]))

(defn start-stop-button [start-fn stop-fn]
  [:div
   [:button {:on-click start-fn} "START"]
   [:button {:on-click stop-fn} "STOP"]])

(defn more-less-neurons-button []
  (let [neuron-count (rf/subscribe [:neuron-count])]
    (fn []
      [:div
       [:button {:on-click #(rf/dispatch [:more-neurons])} "+"]
       [:button {:on-click #(rf/dispatch [:less-neurons])} "-"]
       [:span @neuron-count "Neurons"]])))

(defn main [start-fn stop-fn learning-rate-fn]
  [:div
   [chart/chart]
   [start-stop-button start-fn stop-fn]
   [more-less-neurons-button]
   [time/current-time learning-rate-fn]
   [time/timeout-input]])