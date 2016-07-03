(ns kohonen.ui.chart
  (:require [reagent.core :as r]))

(defonce points (r/atom [{:x 0 :y 0}]))
(defonce stimulus (r/atom {:x 0 :y 0}))
(defonce stimuli (r/atom []))
(defonce nearest (r/atom {:x 0 :y 0}))

(defn set-stimulus [point]
  (reset! stimulus point)
  (swap! stimuli #(take 50 (conj % point))))

(defn set-points [new-points]
  (reset! points new-points))

(defn set-nearest [point]
  (reset! nearest point))


(def segment-defaults
  {:fill         "none"
   :stroke       "blue"
   :stroke-width 2})

(defn- d-point [type {:keys [x y]}]
  (str type x "," y))

(defn- d [coords]
  (apply str (cons (d-point "M" (first coords))
                   (map (partial d-point "L") (rest coords)))))

(defn path [coords]
  [:path
   (merge segment-defaults
          {:stroke-width 4
           :d            (d coords)})])


(defn stimulus-marker [coord]
  [:circle
   (merge segment-defaults
          {:stroke "red"
           :cx     (:x coord)
           :cy     (:y coord)
           :r      10})])

(defn stimuli-marker [points]
  [:g
   (map (fn [p]
          ^{:key p} [:circle
                     (merge segment-defaults
                            {:stroke "#ddd"
                             :fill   "#ddd"
                             :cx     (:x p)
                             :cy     (:y p)
                             :r      1})]) points)])

(defn nearest-marker [point]
  [:circle
   (merge segment-defaults
          {:stroke "orange"
           :cx     (:x point)
           :cy     (:y point)
           :r      5})])

(defn chart []
  [:svg
   {:width  800
    :height 400
    :style  {:border "1px solid black"}}
   [:g
    [stimulus-marker @stimulus]
    [stimuli-marker @stimuli]
    [nearest-marker @nearest]
    [path @points]]])