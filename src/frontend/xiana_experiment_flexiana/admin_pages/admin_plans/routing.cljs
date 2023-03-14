(ns xiana-experiment-flexiana.admin-pages.admin-plans.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.events.plans :as events-plans]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :admin-plans
  [arg]
  (rf/dispatch [::events-plans/select-all-plans])
  (:action arg))
