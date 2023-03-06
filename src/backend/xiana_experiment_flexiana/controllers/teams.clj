(ns xiana-experiment-flexiana.controllers.teams
  (:require
   [xiana-experiment-flexiana.models.teams :as model]
   [xiana-experiment-flexiana.views.teams :as view]))

(defn select-team [state]
  (let [team-id (-> state :request-data :match :path-params :team-id)]
    (-> state
        (assoc :db-queries (model/select-team team-id))
        (assoc :view view/teams))))

(defn select-all-teams [state]
  (-> state
      (assoc :db-queries (model/select-all-teams))
      (assoc :view view/teams)))

(defn update-team [{{params :body-params} :request :as state}]
  (let [team-id (-> state :request-data :match :path-params :team-id)]
    (-> state
        (assoc :db-queries (model/update-team team-id params))
        (assoc :view view/teams))))
