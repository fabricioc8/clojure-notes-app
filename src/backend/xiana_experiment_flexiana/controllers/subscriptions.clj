(ns backend.xiana-experiment-flexiana.controllers.subscriptions
  (:require
   [backend.xiana-experiment-flexiana.models.subscriptions :as sub-mod]))

(defn select-current-team-subscription [state]
  (let [team-id (-> state :request-data :match :path-params :id)]
    (-> state
        (assoc :db-queries (sub-mod/select-current-team-subscription team-id)))))

(defn select-all-subscriptions [state]
  (-> state
      (assoc :db-queries (sub-mod/select-all-subscriptions))))

(defn update-subscription [{{params :body-params} :request :as state}]
  (let [subscription-id (-> state :request-data :match :path-params :id)]
    (-> state
        (assoc :db-queries (sub-mod/update-subscription subscription-id params)))))
