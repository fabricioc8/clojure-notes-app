(ns xiana-experiment-flexiana.subs.invoices
  (:require
   [xiana-experiment-flexiana.subs.app-db :as db]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::team-invoices
 :<- [::db/entity]
 (fn [entity _]
   (get entity :invoices)))
