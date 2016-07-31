(ns kohonen.db)

;; -- Default app-db Value  ---------------------------------------------------

(def default-value
  {:timeout  50
   :t        1
   :neurons  [{:x 0 :y 0} {:x 0 :y 0}]
   :stimulus {:x 0 :y 0}
   :stimuli  []
   :nearest  {:x 0 :y 0}})

;; -- Local Storage  ----------------------------------------------------------

; ...