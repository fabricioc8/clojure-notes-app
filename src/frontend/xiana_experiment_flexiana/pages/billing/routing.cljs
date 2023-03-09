(ns xiana-experiment-flexiana.pages.billing.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.events.invoices :as events-invoices]
   [xiana-experiment-flexiana.events.subscriptions :as events-subscriptions]
   [xiana-experiment-flexiana.events.plans :as events-plans]))

(defmethod routing/handle-route :billing
  [arg]
  (rf/dispatch [::events-subscriptions/select-current-subscription])
  (rf/dispatch [::events-plans/select-team-plans])
  (rf/dispatch [::events-invoices/select-team-invoices])
  (:action arg))
