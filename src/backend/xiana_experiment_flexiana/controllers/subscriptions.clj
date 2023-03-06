(ns xiana-experiment-flexiana.controllers.subscriptions
  (:require
   [xiana-experiment-flexiana.models.subscriptions :as model]
   [xiana-experiment-flexiana.views.subscriptions :as view]))

(defn select-current-team-subscription [state]
  (let [team-id (-> state :request-data :match :path-params :team-id)]
    (-> state
        (assoc :db-queries (model/select-current-team-subscription team-id))
        (assoc :view view/subscriptions))))

(defn select-all-subscriptions [state]
  (-> state
      (assoc :db-queries (model/select-all-subscriptions))
      (assoc :view view/subscriptions)))

(defn update-subscription [{{params :body-params} :request :as state}]
  (let [subscription-id (-> state :request-data :match :path-params :subscription-id)]
    (-> state
        (assoc :db-queries (model/update-subscription subscription-id params))
        (assoc :view view/subscriptions))))
