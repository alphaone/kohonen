(ns kohonen.ui.chart
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))

(def segment-defaults
  {:fill         "none"
   :stroke       "blue"
   :stroke-width 2})

(defn- d-point [type {:keys [x y]}]
  (str type x "," y))

(defn- d [coords]
  (apply str (cons (d-point "M" (first coords))
                   (map (partial d-point "L") (rest coords)))))

(defn neurons-path []
  (let [neurons (rf/subscribe [:neurons])]
    (fn []
      [:path
       (merge segment-defaults
              {:stroke-width 4
               :d            (d @neurons)})])))


(defn stimulus-marker []
  (let [stimulus (rf/subscribe [:stimulus])]
    (fn []
      [:circle
       (merge segment-defaults
              {:stroke "red"
               :cx     (:x @stimulus)
               :cy     (:y @stimulus)
               :r      10})])))

(defn stimuli-marker []
  (let [stimuli (rf/subscribe [:stimuli])]
    (fn []
      [:g
       (for [p @stimuli]
         ^{:key p} [:circle
                    (merge segment-defaults
                           {:stroke "#ddd"
                            :fill   "#ddd"
                            :cx     (:x p)
                            :cy     (:y p)
                            :r      1})])])))

(defn nearest-marker []
  (let [nearest (rf/subscribe [:nearest])]
    (fn []
      [:circle
       (merge segment-defaults
              {:stroke "orange"
               :cx     (:x @nearest)
               :cy     (:y @nearest)
               :r      5})])))

(defn chart []
  [:svg
   {:width  800
    :height 400
    :style  {:border "1px solid black"}}
   [:g
    [stimulus-marker]
    [stimuli-marker]
    [nearest-marker]
    [neurons-path]]])