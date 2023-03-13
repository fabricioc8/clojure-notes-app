(ns xiana-experiment-flexiana.pages.support.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.events.tickets :as events-tickets]
   [xiana-experiment-flexiana.events.ticket-messages :as events-ticket-messages]
   [xiana-experiment-flexiana.pages.support.events :as support-events]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :tickets
  [arg]
  (rf/dispatch [::support-events/ticket-name-input ""])
  (rf/dispatch [::events-tickets/select-team-tickets])
  (rf/dispatch [::events-ticket-messages/select-team-tickets-messages])
  (:action arg))
