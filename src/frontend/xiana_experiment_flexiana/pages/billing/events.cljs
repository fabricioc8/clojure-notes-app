(ns xiana-experiment-flexiana.pages.billing.events
  (:require
   [re-frame.core :as rf]))

(rf/reg-event-db
 ::billing-inputs-values
 (fn [db [_ k v]]
   (update-in db [:view :billing] assoc k v)))

(rf/reg-event-db
 ::reset-billing-input-values
 (fn [db _]
   (update db :view dissoc :billing)))
