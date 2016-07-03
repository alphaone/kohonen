(ns kohonen.ui.current-time
  (:require [reagent.core :as r]))

(defonce current-t (r/atom 0))

(defn set-current-time [t]
  (reset! current-t t))

(defn current-time [learning-rate-fn]
  [:div
   [:div "Time:" @current-t]
   [:div "LearningRate:" (learning-rate-fn @current-t)]])
