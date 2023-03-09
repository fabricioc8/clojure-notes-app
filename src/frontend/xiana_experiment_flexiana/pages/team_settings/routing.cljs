(ns xiana-experiment-flexiana.pages.team-settings.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :team-settings
  [arg]
  (:action arg))
