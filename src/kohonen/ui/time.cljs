(ns kohonen.ui.time
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))

(defn timeout-input []
  (let [to (rf/subscribe [:timeout])]
    (fn []
      [:div
       "Timeout: "
       [:input {:type      "text"
                :value     @to
                :on-change #(rf/dispatch [:set-timeout (-> % .-target .-value)])}]])))

(defn current-time [learning-rate-fn]
  (let [current-t (rf/subscribe [:t])]
    (fn []
      [:div
       [:div "Time:" @current-t]
       [:div "LearningRate:" (learning-rate-fn @current-t)]])))
