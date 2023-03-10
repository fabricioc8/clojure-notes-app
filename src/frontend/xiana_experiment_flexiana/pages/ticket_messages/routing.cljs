(ns xiana-experiment-flexiana.pages.ticket-messages.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.events.ticket-messages :as events-ticket-messages]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :ticket-chat
  [{:keys [action params]}]
  (rf/dispatch [::events-ticket-messages/select-ticket-messages (:ticket-id params)])
  [action (:ticket-id params)])
