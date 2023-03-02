(ns backend.xiana-experiment-flexiana.models.teams
  (:require
   [backend.xiana-experiment-flexiana.models.common :refer [->UUID] :as mc]
   [honeysql.core :as sql]
   [honeysql.helpers :as sqlh]))

(defn select-team [team-id]
  {:queries [{:select [:*]
              :from [:teams]
              :where [:= :id (->UUID team-id)]}]})

(defn select-all-teams []
  {:queries [{:select [:*]
              :from [:teams]}]})

(defn update-team [team-id params]
  {:queries [{:update :teams
              :set [(mc/->team params)]
              :where [:= :id (->UUID team-id)]}]})
