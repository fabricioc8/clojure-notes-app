(ns backend.xiana-experiment-flexiana.models.subscriptions
  (:require
   [backend.xiana-experiment-flexiana.models.common :refer [->UUID] :as mc]
   [honeysql.core :as sql]
   [honeysql.helpers :as sqlh]))

(defn select-subscription [team-id]
  {:queries [{:select [:*]
              :from [:subscriptions]
              :where [:= :team-id (->UUID team-id)]}]})

(defn select-all-subscriptions []
  {:queries [{:select [:*]
              :from [:subscriptions]}]})

(defn update-subscription [team-id params]
  {:queries [{:update :subscriptions
              :set [(mc/->subscription params)]
              :where [:= :team-id (->UUID team-id)]}]})
