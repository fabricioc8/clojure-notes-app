(ns backend.xiana-experiment-flexiana.controllers.messages
  (:require
   [backend.xiana-experiment-flexiana.models.messages :as model]
   [backend.xiana-experiment-flexiana.views.messages :as view]))

(defn insert-ticket-message [{{params :body-params} :request :as state}]
  (-> state
      (assoc :db-queries (model/insert-ticket-message params))
      (assoc :view view/messages)))

(defn select-ticket-messages [state]
  (let [ticket-id (-> state :request-data :match :path-params :ticket-id)]
    (-> state
        (assoc :db-queries (model/select-ticket-messages ticket-id))
        (assoc :view view/messages))))
