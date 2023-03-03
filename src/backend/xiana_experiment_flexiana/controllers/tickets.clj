(ns backend.xiana-experiment-flexiana.controllers.tickets
  (:require
   [backend.xiana-experiment-flexiana.models.tickets :as tick-mod]))

(defn insert-ticket [{{params :body-params} :request :as state}]
  (-> state
      (assoc :db-queries (tick-mod/insert-ticket params))))

(defn select-team-tickets [state]
  (let [team-id (-> state :request-data :match :path-params :id)]
    (-> state
        (assoc :db-queries (tick-mod/select-team-tickets team-id)))))

(defn update-ticket [{{params :body-params} :request :as state}]
  (let [ticket-id (-> state :request-data :match :path-params :id)]
    (-> state
        (assoc :db-queries (tick-mod/update-ticket ticket-id params)))))
