(ns xiana-experiment-flexiana.subs.billing-details
  (:require
   [xiana-experiment-flexiana.subs.app-db :as subs-db]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::select-billing-details
 :<- [::subs-db/entity]
 (fn [entity _]
   (get entity :billing-details)))
