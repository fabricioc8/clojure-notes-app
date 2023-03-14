(ns xiana-experiment-flexiana.subs.tickets
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.subs.app-db :as subs-db]))

(rf/reg-sub
 ::select-all-tickets
 :<- [::subs-db/entity]
 (fn [entity _]
   (get entity :all-tickets)))
