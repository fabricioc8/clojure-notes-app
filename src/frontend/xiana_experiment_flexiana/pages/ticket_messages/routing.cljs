(ns xiana-experiment-flexiana.pages.ticket-messages.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.events.tickets :as events-tickets]
   [xiana-experiment-flexiana.pages.support.events :as support-events]
   [xiana-experiment-flexiana.events.ticket-messages :as events-ticket-messages]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :ticket-chat
  [{:keys [action params]}]
  (prn "AAA" action params)
  (rf/dispatch [::events-ticket-messages/select-ticket-messages (:ticket-id params) #_"95ebd9fb-9615-480b-9b48-d8d45139ba25"])
  #_action
  #_{:action action :params params}
  [action (:ticket-id params)]
  #_(:action arg))
