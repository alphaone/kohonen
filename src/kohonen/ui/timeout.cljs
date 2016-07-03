(ns kohonen.ui.timeout
  (:require [reagent.core :as r]))

(defonce timeout (r/atom 1000))

(defn timeout-input []
  [:div
   "Timeout: "
   [:input {:type      "text"
            :value     @timeout
            :on-change #(reset! timeout (-> % .-target .-value))}]])
