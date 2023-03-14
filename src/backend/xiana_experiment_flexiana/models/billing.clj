(ns xiana-experiment-flexiana.models.billing
  (:require
   [xiana-experiment-flexiana.models.common :refer [->UUID next-uuid] :as mc]
   [honeysql.core :as sql]
   [honeysql.helpers :as sqlh]))

(defn insert-billing [params]
  (let [new-billing-id (next-uuid)
        billing-params (-> params
                           (assoc :id new-billing-id)
                           mc/->billing)]
    {:queries [{:insert-into :billing-details
                :values [billing-params]}]}))

(defn select-team-billings [team-id]
  {:queries [{:select [:*]
              :from [:billing-details]
              :where [:= :team-id (->UUID team-id)]}]})

(defn update-billing [team-id params]
  {:queries [{:update :billing-details
              :set (mc/->billing params)
              :where [:= :team-id (->UUID team-id)]}]})
