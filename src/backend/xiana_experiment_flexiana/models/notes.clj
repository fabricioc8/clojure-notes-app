(ns backend.xiana-experiment-flexiana.models.notes
  (:require
   [backend.xiana-experiment-flexiana.models.common :refer [->UUID next-uuid]]
   [honeysql.core :as sql]
   [honeysql.helpers :as sqlh]))

(defn- ->note
  [params]
  (let [params (select-keys params [:id :team-id :is-public :name :content])]
    (cond-> params
      (:id params) (update :id ->UUID)
      (:team-id params) (update :team-id ->UUID))))

(defn insert-note [params]
  (let [new-note-id (next-uuid)
        note-params (-> params
                        (assoc :id new-note-id))]
    {:queries [{:insert-into :notes
                :values [note-params]}]}))

(defn select-note [note-id]
  {:queries [{:select [:*]
              :from [:notes]
              :where [:= :id (->UUID note-id)]}]})

(defn update-note [note-id params]
  {:queries [{:update :notes
              :set (->note params)
              :where [:= :id (->UUID note-id)]}]})

(defn selete-note [note-id]
  {:queries [{:delete-from :notes
              :where [:= :id (->UUID note-id)]}]})
