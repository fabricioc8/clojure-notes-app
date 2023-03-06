(ns xiana-experiment-flexiana.models.subscriptions
  (:require
   [xiana-experiment-flexiana.models.common :refer [->UUID] :as mc]
   [honeysql.core :as sql]
   [honeysql.helpers :as sqlh]))

(defn select-current-team-subscription [team-id]
  {:queries [{:select [:*]
              :from [:subscriptions]
              :where [:and
                      [:= :team-id (->UUID team-id)]
                      [:= :canceled false ]]}]})

(defn select-all-subscriptions []
  {:queries [{:select [:*]
              :from [:subscriptions]}]})

(defn update-subscription [subscription-id params]
  {:queries [{:update :subscriptions
              :set (mc/->subscription params)
              :where [:= :id (->UUID subscription-id)]}]})
