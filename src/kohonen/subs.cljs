(ns kohonen.subs
  (:require-macros [reagent.ratom :as r])
  (:require [re-frame.core :as rf]))

(rf/register-sub
  :timeout
  (fn [db _]
    (r/reaction (:timeout @db))))

(rf/register-sub
  :t
  (fn [db _]
    (r/reaction (:t @db))))

(rf/register-sub
  :neurons
  (fn [db _]
    (r/reaction (:neurons @db))))

(rf/register-sub
  :stimulus
  (fn [db _]
    (r/reaction (:stimulus @db))))

(rf/register-sub
  :stimuli
  (fn [db _]
    (r/reaction (:stimuli @db))))

(rf/register-sub
  :nearest
  (fn [db _]
    (r/reaction (:nearest @db))))
