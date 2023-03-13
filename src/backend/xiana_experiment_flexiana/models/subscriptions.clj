(ns xiana-experiment-flexiana.models.subscriptions
  (:require
   [xiana-experiment-flexiana.models.common :refer [->UUID next-uuid] :as mc]
   [honeysql.core :as sql]
   [honeysql.helpers :as sqlh]))

(defn select-current-team-subscription [team-id]
  {:queries [{:select [:s.id :s.team-id [:p.id :plan-id] :p.max-users]
              :from [[:subscriptions :s]
                     [:plans :p]]
              :where [:and
                      [:= :s.team-id (->UUID team-id)]
                      [:= :s.canceled false]
                      [:= :s.plan-id :p.id]]}
             #_{:select [:*]
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

(defn insert-subscription [params]
  (let [new-subscription-id (next-uuid)
        subscription-params (-> params
                                (assoc :id new-subscription-id)
                                mc/->subscription)]
    {:queries [{:update :subscriptions
                :set {:canceled true}
                :where [:= :team-id (->UUID (:team-id params))]}
               {:insert-into :subscriptions
                :values [subscription-params]}]}))
