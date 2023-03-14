(ns xiana-experiment-flexiana.pages.billing.subs
  (:require
   [xiana-experiment-flexiana.subs.app-db :as app-db]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::billing-inputs-values
 :<- [::app-db/view]
 (fn [view [_]]
   (get view :billing)))
