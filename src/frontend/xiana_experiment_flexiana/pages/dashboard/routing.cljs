(ns xiana-experiment-flexiana.pages.dashboard.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.events.notes :as notes]
   [xiana-experiment-flexiana.pages.dashboard.events :as dashboard-events]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :dashboard
  [arg]
  (rf/dispatch [::dashboard-events/reset-note-title-input])
  ;(rf/dispatch [::notes/select-team-notes])
  (:action arg))
