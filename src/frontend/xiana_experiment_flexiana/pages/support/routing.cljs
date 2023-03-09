(ns xiana-experiment-flexiana.pages.support.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.events.tickets :as events-tickets]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :tickets
  [arg]
  (rf/dispatch [::events-tickets/select-team-tickets])
  (:action arg))
