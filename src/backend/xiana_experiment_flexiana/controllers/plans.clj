(ns backend.xiana-experiment-flexiana.controllers.plans
  (:require
   [backend.xiana-experiment-flexiana.models.plans :as pl-mod]))

(defn insert-plan [{{params :body-params} :request :as state}]
  (-> state
      (assoc :db-queries (pl-mod/insert-plan params))))

(defn select-all-plans [state]
  (-> state
      (assoc :db-queries (pl-mod/select-all-plans))))

(defn select-team-plans [state]
  (let [team-id (-> state :request-data :match :path-params :id)]
    (-> state
        (assoc :db-queries (pl-mod/select-team-plans team-id)))))

(defn update-plan [{{params :body-params} :request :as state}]
  (let [plan-id (-> state :request-data :match :path-params :id)]
    (-> state
        (assoc :db-queries (pl-mod/update-plan plan-id params)))))
