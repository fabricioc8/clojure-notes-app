(ns backend.xiana-experiment-flexiana.controllers.messages
  (:require
   [backend.xiana-experiment-flexiana.models.messages :as mes-mod]))

(defn insert-ticket-message [{{params :body-params} :request :as state}]
  (-> state
      (assoc :db-queries (mes-mod/insert-ticket-message params))))

(defn select-ticket messages [state]
  (let [ticket-id (-> state :request-data :match :path-params :id)]
    (-> state
        (assoc :db-queries (mes-mod/select-ticket-messages ticket-id)))))
