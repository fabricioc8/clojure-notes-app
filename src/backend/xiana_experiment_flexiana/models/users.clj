(ns backend.xiana-experiment-flexiana.models.users
  (:require
   [backend.xiana-experiment-flexiana.models.common :refer [->UUID next-uuid] :as mc]
   [honeysql.core :as sql]
   [honeysql.helpers :as sqlh]))

(defn insert-user-by-themselve [params]
  (let [new-user-id (next-uuid)
        new-team-id (next-uuid)
        new-plan-id (next-uuid)
        new-subscription-id (next-uuid)
        user-params (-> params
                        (assoc :id new-user-id)
                        mc/->user)
        new-team (-> params
                     (assoc :id new-team-id)
                     mc/->team)
        team-users-params (mc/->team-user {:user-id new-user-id :team-id new-team-id :team-role "team-admin"})
        plan-params (mc/->plan {:id new-plan-id :name "Free" :price-usd 0 :max-notes 3 :max-chars 100 :max-users 2 :team-id new-team-id})
        subscriptions-params (mc/->subscription {:id new-subscription-id :team-id new-team-id :plan-id new-plan-id})]
    {:queries [{:insert-into :users
                :values [user-params]}
               {:insert-into :teams
                :values [new-team]}
               {:insert-into :team-users
                :values [team-users-params]}
               {:insert-into :plans
                :values [plan-params]}
               {:insert-into :subscriptions
                :values [subscriptions-params]}]
     :transaction? true}))

(defn insert-user-by-invitation [params]
  (let [new-user-id (next-uuid)
        users-params (-> params
                         (assoc :id new-user-id)
                         mc/->user)
        team-users-params (-> params
                              (assoc :user-id new-user-id)
                              mc/->team-user)]
    {:queries [{:insert-into :users
                :values [users-params]}
               {:insert-into :team-users
                :values [team-users-params]}]
     :transaction? true}))

(defn select-user [user-id]
  {:queries [{:select [:*]
              :from [:users]
              :where [:= :id (->UUID user-id)]}]})

(defn select-all-users []
  {:queries [{:select [:*]
              :from [:users]}]})

(defn select-team-users [team-id]
  {:queries [{:select [:*]
              :from [:users]
              :where [:in :id {:select [:user-id]
                               :from [:team-users]
                               :where [:= :team-id (->UUID team-id)]}]}]})

(defn update-user [user-id params]
  {:queries [{:update :users
              :set (mc/->user params)
              :where [:= :id (->UUID user-id)]}]})
