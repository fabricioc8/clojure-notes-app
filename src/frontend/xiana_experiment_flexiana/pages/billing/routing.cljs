(ns xiana-experiment-flexiana.pages.billing.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :billing
  [arg]
  (:action arg))
