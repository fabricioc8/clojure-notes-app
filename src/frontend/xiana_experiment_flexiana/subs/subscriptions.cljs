(ns xiana-experiment-flexiana.subs.subscriptions
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.subs.app-db :as subs-db]))

(rf/reg-sub
 ::select-current-subscription
 :<- [::subs-db/entity]
 (fn [entity _]
   (get entity :current-subscription)))

(rf/reg-sub
 ::select-all-subscriptions
 :<- [::subs-db/entity]
 (fn [entity _]
   (get entity :subscriptions)))

(rf/reg-sub
 ::active-subscriptions
 :<- [::select-all-subscriptions]
 (fn [all-subscriptions _]
   (remove :canceled all-subscriptions)))
