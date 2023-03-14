(ns xiana-experiment-flexiana.admin-pages.admin-dashboard.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.events.notes :as events-notes]
   [xiana-experiment-flexiana.events.users :as events-users]
   [xiana-experiment-flexiana.events.teams :as events-teams]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :admin-dashboard
  [arg]
  (rf/dispatch [::events-teams/select-all-teams])
  (rf/dispatch [::events-users/select-all-users])
  (rf/dispatch [::events-notes/select-all-notes])
  (:action arg))
