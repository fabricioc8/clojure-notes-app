(ns backend.xiana-experiment-flexiana.controllers.notes
  (:require
   [backend.xiana-experiment-flexiana.models.notes :as not-mod]))

(defn insert-note [{{params :body-params} :request :as state}]
  (-> state
   (assoc :db-queries (not-mod/insert-note params))))

(defn select-note [state]
  (let [note-id (-> state :request-data :match :path-params :id)]
    (-> state
        (assoc :db-queries (not-mod/select-note note-id)))))

(defn select-all-notes [state]
  (-> state
   (assoc :db-queries (not-mod/select-all-notes))))

(defn select-team-notes [state]
  (let [team-id (-> state :request-data :match :path-params :id)]
   (-> state
       (assoc :db-queries (not-mod/select-team-notes team-id)))))

(defn update-note [{{params :body-params} :request :as state}]
  (let [note-id (-> state :request-data :match :path-params :id)]
    (-> state
        (assoc :db-queries (not-mod/update-note note-id params)))))

(defn delete-note [state]
  (let [note-id (-> state :request-data :match :path-params :id)]
    (-> state
        (assoc :db-queries (not-mod/delete-note note-id)))))
