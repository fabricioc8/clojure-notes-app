(ns xiana-experiment-flexiana.models.notes
  (:require
   [xiana-experiment-flexiana.models.common :refer [->UUID next-uuid] :as mc]
   [honeysql.core :as sql]
   [honeysql.helpers :as sqlh]))

(defn insert-note [params]
  (let [new-note-id (next-uuid)
        note-params (-> params
                        (assoc :id new-note-id)
                        mc/->note)]
    {:queries [{:insert-into :notes
                :values [note-params]}]}))

(defn select-note [note-id]
  {:queries [{:select [:*]
              :from [:notes]
              :where [:= :id (->UUID note-id)]}]})

(defn select-all-notes []
  {:queries [{:select [:*]
              :from [:notes]}]})

(defn select-team-notes [team-id]
  {:queries [{:select [:*]
              :from [:notes]
              :where [:= :team-id (->UUID team-id)]}]})

(defn update-note [note-id params]
  {:queries [{:update :notes
              :set (mc/->note params)
              :where [:= :id (->UUID note-id)]}]})

(defn delete-note [note-id]
  {:queries [{:delete-from :notes
              :where [:= :id (->UUID note-id)]}]})
