(ns xiana-experiment-flexiana.admin-pages.admin-teams.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.events.plans :as events-plans]
   [xiana-experiment-flexiana.events.teams :as events-teams]
   [xiana-experiment-flexiana.events.subscriptions :as events-subscriptions]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :admin-teams
  [arg]
  (rf/dispatch [::events-plans/select-all-plans])
  (rf/dispatch [::events-teams/select-all-teams])
  (rf/dispatch [::events-subscriptions/select-all-subscriptions])
  (:action arg))
