(ns backend.xiana-experiment-flexiana.controllers.plans
  (:require
   [backend.xiana-experiment-flexiana.models.plans :as model]
   [backend.xiana-experiment-flexiana.views.plans :as view]))

(defn insert-plan [{{params :body-params} :request :as state}]
  (-> state
      (assoc :db-queries (model/insert-plan params))
      (assoc :view view/plans)))

(defn select-all-plans [state]
  (-> state
      (assoc :db-queries (model/select-all-plans))
      (assoc :view view/plans)))

(defn select-team-plans [state]
  (let [team-id (-> state :request-data :match :path-params :team-id)]
    (-> state
        (assoc :db-queries (model/select-team-plans team-id))
        (assoc :view view/plans))))

(defn update-plan [{{params :body-params} :request :as state}]
  (let [plan-id (-> state :request-data :match :path-params :plan-id)]
    (-> state
        (assoc :db-queries (model/update-plan plan-id params))
        (assoc :view view/plans))))
