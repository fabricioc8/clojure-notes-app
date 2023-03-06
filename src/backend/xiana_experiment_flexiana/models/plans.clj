(ns xiana-experiment-flexiana.models.plans
  (:require
   [xiana-experiment-flexiana.models.common :refer [->UUID next-uuid] :as mc]
   [honeysql.core :as sql]
   [honeysql.helpers :as sqlh]))

(defn insert-plan [params]
  (let [new-plan-id (next-uuid)
        plan-params (-> params
                        (assoc :id new-plan-id)
                        mc/->plan)]
    {:queries [{:insert-into :plans
                :values [plan-params]}]}))

(defn select-all-plans []
  {:queries [{:select [:*]
              :from [:plans]}]})

(defn select-team-plans [team-id]
  {:queries [{:select [:*]
              :from [:plans]
              :where [:or
                      [:= :team-id (->UUID team-id)]
                      [:= :public true]]}]})

(defn update-plan [plan-id params]
  {:queries [{:update :plans
              :set (mc/->plan params)
              :where [:= :id (->UUID plan-id)]}]})
