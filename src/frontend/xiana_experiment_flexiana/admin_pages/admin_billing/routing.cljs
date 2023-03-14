(ns xiana-experiment-flexiana.admin-pages.admin-billing.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.events.subscriptions :as events-subscriptions]
   [xiana-experiment-flexiana.events.invoices :as events-invoices]
   [xiana-experiment-flexiana.events.plans :as events-plans]
   [xiana-experiment-flexiana.events.teams :as events-teams]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :admin-billing
  [arg]
  (rf/dispatch [::events-subscriptions/select-all-subscriptions])
  (rf/dispatch [::events-invoices/select-all-invoices])
  (rf/dispatch [::events-plans/select-all-plans])
  (rf/dispatch [::events-teams/select-all-teams])
  (:action arg))
