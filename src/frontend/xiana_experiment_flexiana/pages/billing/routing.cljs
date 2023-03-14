(ns xiana-experiment-flexiana.pages.billing.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.events.invoices :as events-invoices]
   [xiana-experiment-flexiana.events.subscriptions :as events-subscriptions]
   [xiana-experiment-flexiana.events.billing-details :as events-billings]
   [xiana-experiment-flexiana.events.plans :as events-plans]
   [xiana-experiment-flexiana.pages.billing.events :as view-events]))

(defmethod routing/handle-route :billing
  [arg]
  (rf/dispatch [::events-subscriptions/select-current-subscription])
  (rf/dispatch [::events-plans/select-team-plans])
  (rf/dispatch [::events-invoices/select-team-invoices])
  (rf/dispatch [::events-billings/select-team-billing-details])
  (rf/dispatch [::view-events/reset-billing-input-values])
  (:action arg))
