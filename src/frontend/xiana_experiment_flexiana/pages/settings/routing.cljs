(ns xiana-experiment-flexiana.pages.settings.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.events.notes :as notes]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :tasks
  [arg]
  (rf/dispatch [::notes/select-team-notes "3c033fb5-162c-4bb7-b8ea-8a4a26019409"])
  (:action arg))
