(ns kohonen.handlers
  (:require [kohonen.db :as db]
            [re-frame.core :refer [register-handler path trim-v after debug]]
            [kohonen.kohonen :as k]))

(register-handler
  :initialize-db
  (fn [_ _]
    db/default-value))

(register-handler
  :set-timeout
  [(path :timeout) trim-v]
  (fn [old-timeout [new-timeout]]
    new-timeout))

(register-handler
  :inc-time
  [(path :t) trim-v]
  (fn [old-t]
    (inc old-t)))

(register-handler
  :set-neurons
  [(path :neurons) trim-v]
  (fn [_ [new-neurons]]
    new-neurons))

(register-handler
  :set-stimulus
  (fn [db [_ new-stimulus]]
    (-> db
        (assoc :stimulus new-stimulus)
        (update :stimuli #(take 50 (conj % new-stimulus))))))

(register-handler
  :set-nearest
  [(path :nearest) trim-v]
  (fn [_ [new-nearest]]
    new-nearest))

(register-handler
  :more-neurons
  [(path :neurons)]
  (fn [old-neurons]
    (conj old-neurons (k/random-point))))

(register-handler
  :less-neurons
  [(path :neurons)]
  (fn [old-neurons]
    (rest old-neurons)))