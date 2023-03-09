(ns xiana-experiment-flexiana.pages.dashboard.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.events.notes :as notes]
   [xiana-experiment-flexiana.subs.users :as subs-users]
   [xiana-experiment-flexiana.events.users :as users]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :dashboard
  [arg]
  ;; (let [user-id @(rf/subscribe [::subs-users/session-user-id])
  ;;       _ (rf/dispatch [::users/select-user-team user-id])
  ;;       team-id @(rf/subscribe [::subs-users/user-team])]
  ;;   (rf/dispatch [::notes/select-team-notes team-id]))
  (rf/dispatch [::notes/select-team-notes])
  (:action arg))
