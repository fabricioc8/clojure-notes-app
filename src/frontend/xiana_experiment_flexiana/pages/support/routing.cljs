(ns xiana-experiment-flexiana.pages.support.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :support
  [arg]
  (:action arg))
