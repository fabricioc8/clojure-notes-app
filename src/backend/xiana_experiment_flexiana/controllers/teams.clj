(ns backend.xiana-experiment-flexiana.controllers.teams
  (:require
   [backend.xiana-experiment-flexiana.models.teams :as team-mod]))

(defn select-team [state]
  (let [team-id (-> state :request-data :match :path-params :id)] (-> state
        (assoc :db-queries (team-mod/select-team team-id)))))

(defn select-all-teams [state]
  (-> state
      (assoc :db-queries (team-mod/select-all-teams))))

(defn update-team [{{params :body-params} :request :as state}]
  (let [team-id (-> state :request-data :match :path-params :id)]
    (-> state
        (assoc :db-queries (team-mod/update-team team-id params)))))
