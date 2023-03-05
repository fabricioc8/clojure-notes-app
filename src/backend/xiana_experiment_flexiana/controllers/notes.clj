(ns backend.xiana-experiment-flexiana.controllers.notes
  (:require
   [backend.xiana-experiment-flexiana.models.notes :as model]
   [backend.xiana-experiment-flexiana.views.notes :as view]))

(defn insert-note [{{params :body-params} :request :as state}]
  (-> state
      (assoc :db-queries (model/insert-note params))
      (assoc :view view/notes)))

(defn select-note [state]
  (let [note-id (-> state :request-data :match :path-params :note-id)]
    (-> state
        (assoc :db-queries (model/select-note note-id))
        (assoc :view view/notes))))

(defn select-all-notes [state]
  (-> state
      (assoc :db-queries (model/select-all-notes))
      (assoc :view view/notes)))

(defn select-team-notes [state]
  (let [team-id (-> state :request-data :match :path-params :team-id)]
   (-> state
       (assoc :db-queries (model/select-team-notes team-id))
       (assoc :view view/notes))))

(defn update-note [{{params :body-params} :request :as state}]
  (let [note-id (-> state :request-data :match :path-params :note-id)]
    (-> state
        (assoc :db-queries (model/update-note note-id params))
        (assoc :view view/notes))))

(defn delete-note [state]
  (let [note-id (-> state :request-data :match :path-params :note-id)]
    (-> state
        (assoc :db-queries (model/delete-note note-id))
        (assoc :view view/notes))))
