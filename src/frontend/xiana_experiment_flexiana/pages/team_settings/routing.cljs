(ns xiana-experiment-flexiana.pages.team-settings.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.pages.team-settings.events :as ts-events]
   [xiana-experiment-flexiana.events.users :as events-users]
   [xiana-experiment-flexiana.events.subscriptions :as events-subscriptions]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :team-settings
  [arg]
  (rf/dispatch [::events-users/select-team-users])
  (rf/dispatch [::ts-events/team-name-input nil])
  (rf/dispatch [::events-subscriptions/select-current-subscription])
  ;(rf/dispatch [::events-teams/select-user-team])
  (:action arg))
