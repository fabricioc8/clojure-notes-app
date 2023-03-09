(ns xiana-experiment-flexiana.pages.settings.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.events.notes :as notes]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :tasks
  [arg]
  (:action arg))
