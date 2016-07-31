(ns kohonen.kohonen)

(defn random-point []
  {:x (rand-int 800)
   :y (rand-int 400)})

(defn delta [a b]
  {:dx (- (:x b) (:x a))
   :dy (- (:y b) (:y a))})

(defn dist [a b]
  (let [{:keys [dx dy]} (delta a b)]
    (Math/sqrt (+ (* dx dx) (* dy dy)))))

(defn find-nearest [target {:keys [min-dist] :as acc} [i n]]
  (let [current-dist (dist n target)]
    (if (> min-dist current-dist)
      {:min-dist      current-dist
       :nearest       n
       :nearest-index i}
      acc)))

(defn nearest [target neurons]
  (-> (reduce (partial find-nearest target) {:min-dist 9999} (map-indexed vector neurons))
      (select-keys [:nearest-index :nearest])
      vals))

(defn move-to [target n factor]
  (let [delta (delta n target)]
    (-> n
        (update :x + (* factor (:dx delta)))
        (update :y + (* factor (:dy delta))))))

(defn normal-distribution [x µ σ]
  (* (/ 1 (* σ (Math/sqrt (* 2 Math/PI))))
     (Math/exp (* -0.5 (Math/pow (/ (- x µ) σ) 2)))))

(defn adaption-factors [nearest-index length t learning-rate-fn]
  (map (fn [x]
         (* (learning-rate-fn t)
            (* 2 (normal-distribution x nearest-index 2))))
       (range 0 length)))

(defn apply-stimulus [stimulus neurons t learning-rate-fn]
  (let [[nearest-index nearest] (nearest stimulus neurons)
        factors (adaption-factors nearest-index (count neurons) t learning-rate-fn)]
    (map (partial move-to stimulus) neurons factors))
  )