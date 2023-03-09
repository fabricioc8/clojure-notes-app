(ns xiana-experiment-flexiana.pages.team-settings.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.pages.team-settings.events :as ts-events]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :team-settings
  [arg]
  (rf/dispatch [::ts-events/reset-team-name-input])
  (:action arg))
