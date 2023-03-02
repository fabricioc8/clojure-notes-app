(ns backend.xiana-experiment-flexiana.models.users
  (:require
   [backend.xiana-experiment-flexiana.models.common :refer [->UUID next-uuid]]
   [honeysql.core :as sql]
   [honeysql.helpers :as sqlh]))

(defn- ->user
  [params]
  (let [params (select-keys params [:id :email :password :enabled :user-role :api-token])]
    (cond-> params
      (:id params) (update :id ->UUID)
      (:team-id params) (update :team-id ->UUID))))

(defn- ->team
  [params]
  (let [params (select-keys params [:id :name :enabled])]
    (cond-> params
      (:id params) (update :id ->UUID))))

(defn- ->team-user
  [params]
  (let [params (select-keys params [:team_id :user-id :team-role])]
    (cond-> params
      (:team-id params) (update :team-id ->UUID)
      (:user-id params) (update :user-id ->UUID))))

(defn- ->subscription [params]
  (let [params (select-keys params [:id :team-id :plan-id :canceled])]
    (cond-> params
      (:id params) (update :id ->UUID)
      (:plan-id params) (update :plan-id ->UUID))))

(defn insert-user-by-themselve [params]
  (let [new-user-id (next-uuid)
        new-team-id (next-uuid)
        new-subscription-id (next-uuid)
        user-params (-> params
                        (assoc :id new-user-id)
                        ->user)
        new-team (->team {:id new-team-id :name "New team"})
        team-users-params (->team-user {:user-id new-user-id :team-id new-team-id :team-role "admin"})
        subscriptions-params (->subscription {:id new-subscription-id :plan-id "cambiar por id del plan free"})]
    {:queries [{:insert-into :users
                :values [user-params]}
               {:insert-into :teams
                :values [new-team]}
               {:insert-into :team-users
                :values [team-users-params]}
               {:insert-into :subscriptions
                :values [subscriptions-params]}]}))

(defn insert-user-by-invitation [params]
  (let [new-user-id (next-uuid)
        users-params (-> params
                         (assoc :id new-user-id)
                         ->user)
        team-users-params (-> params
                              (assoc :user-id new-user-id)
                              ->team-user)]
    {:queries [{:insert-into :users
                :values [users-params]}
               {:insert-into :team-users
                :values [team-users-params]}]}))

(defn select-user [user-id]
  {:queries [{:select [:*]
              :from [:users]
              :where [:= :id (->UUID user-id)]}]})

(defn select-all-users []
  {:queries [{:select [:*]
              :from [:users]}]})

(defn select-team-users [team-id]
  {:queries [{:with [:tu {:select [:user-id]
                          :from [:team-users]
                          :where [:= :team-id (->UUID team-id)]}]
              :select [:*]
              :from [:users]
              :where [:in :id :tu.user-id]}]})

(defn update-user [user-id params]
  {:queries [{:update :users
              :set (->user params)
              :where [:= :id (->UUID user-id)]}]})

(defn delete-user [user-id]
  {:queries [{:delete-from :users
              :where [:= :id (->UUID user-id)]}]})
;;ver si puedo hacer algo para transformar a #uuid los id que vienen en los uri oara no tener que escribirlo para cada uno,
