(ns xiana-experiment-flexiana.controllers.billing
  (:require
   [xiana-experiment-flexiana.models.billing :as model]
   [xiana-experiment-flexiana.views.billing :as view]))

(defn insert-billing [{{params :body-params} :request :as state}]
  (-> state
      (assoc :db-queries (model/insert-billing params))
      (assoc :view view/billing-details)))

(defn select-team-billings [state]
  (let [team-id (-> state :request-data :match :path-params :team-id)]
    (-> state
        (assoc :db-queries (model/select-team-billings team-id))
        (assoc :view view/billing-details))))

(defn update-billing [{{params :body-params} :request :as state}]
  (let [team-id (-> state :request-data :match :path-params :team-id)]
    (-> state
        (assoc :db-queries (model/update-billing team-id params))
        (assoc :view view/billing-details))))
