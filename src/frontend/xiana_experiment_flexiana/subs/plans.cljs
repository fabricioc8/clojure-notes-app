(ns xiana-experiment-flexiana.subs.plans
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.subs.subscriptions :as subs-subscriptions]
   [xiana-experiment-flexiana.subs.app-db :as subs-db]))

(rf/reg-sub
 ::team-plans
 :<- [::subs-db/entity]
 (fn [entity _]
   (get entity :team-plans)))

(rf/reg-sub
 ::team-plan-name
 :<- [::team-plans]
 :<- [::subs-subscriptions/select-current-subscription]
 (fn [[team-plans current-subscription] _]
   (->> team-plans
        (filter (fn [p] (= (:id p) (:plan-id current-subscription))))
        first
        :name)))
