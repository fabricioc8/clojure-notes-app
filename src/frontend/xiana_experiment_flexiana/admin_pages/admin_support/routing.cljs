(ns xiana-experiment-flexiana.admin-pages.admin-support.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.events.tickets :as events-tickets]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :admin-tickets
  [arg]
  (rf/dispatch [::events-tickets/select-all-tickets])
  (:action arg))
