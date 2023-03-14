(ns xiana-experiment-flexiana.admin-pages.admin-users.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.events.users :as events-users]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :admin-users
  [arg]
  (rf/dispatch [::events-users/select-all-users])
  (rf/dispatch [::events-users/select-teams-users])
  (:action arg))
