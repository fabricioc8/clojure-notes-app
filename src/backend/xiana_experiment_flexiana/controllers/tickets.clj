(ns xiana-experiment-flexiana.controllers.tickets
  (:require
   [xiana-experiment-flexiana.models.tickets :as model]
   [xiana-experiment-flexiana.views.tickets :as view]))

(defn insert-ticket [{{params :body-params} :request :as state}]
  (-> state
      (assoc :db-queries (model/insert-ticket params))
      (assoc :view view/tickets)))

(defn select-team-tickets [state]
  (let [team-id (-> state :request-data :match :path-params :team-id)]
    (-> state
        (assoc :db-queries (model/select-team-tickets team-id))
        (assoc :view view/tickets))))

(defn select-all-tickets [state]
  (-> state
      (assoc :db-queries (model/select-all-tickets))
      (assoc :view view/tickets)))

(defn update-ticket [{{params :body-params} :request :as state}]
  (let [ticket-id (-> state :request-data :match :path-params :ticket-id)]
    (-> state
        (assoc :db-queries (model/update-ticket ticket-id params))
        (assoc :view view/tickets))))
