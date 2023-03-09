(ns xiana-experiment-flexiana.pages.team-settings.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.pages.team-settings.events :as ts-events]
   [xiana-experiment-flexiana.events.users :as events-users]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :team-settings
  [arg]
  (rf/dispatch [::events-users/select-team-users])
  (rf/dispatch [::ts-events/reset-team-name-input])
  (:action arg))
