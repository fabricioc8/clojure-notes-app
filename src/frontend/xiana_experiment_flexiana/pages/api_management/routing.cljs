(ns xiana-experiment-flexiana.pages.api-management.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :api-management
  [arg]
  (:action arg))
