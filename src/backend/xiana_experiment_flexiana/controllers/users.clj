(ns xiana-experiment-flexiana.controllers.users
  (:require
   [xiana-experiment-flexiana.models.users :as model]
   [xiana-experiment-flexiana.views.users :as view]))

(defn insert-user-by-themselve [{{params :body-params} :request :as state}]
  (-> state
      (assoc :db-queries (model/insert-user-by-themselve params))
      (assoc :view view/users)))

(defn insert-user-by-invitation [{{params :body-params} :request :as state}]
  (-> state
      (assoc :db-queries (model/insert-user-by-invitation params))
      (assoc :view view/users)))

(defn select-user[state]
  (let [user-id (-> state :request-data :match :path-params :user-id)]
    (-> state
        (assoc :db-queries (model/select-user user-id))
        (assoc :view view/users))))

(defn select-all-users [state]
  (-> state
      (assoc :db-queries (model/select-all-users))
      (assoc :view view/users)))

(defn update-user [{{params :body-params} :request :as state}] 
  (let [user-id (-> state :request-data :match :path-params :user-id)]
    (-> state
        (assoc :db-queries (model/update-user user-id params))
        (assoc :view view/users))))

(defn select-user-team [state]
  (let [user-id (-> state :request-data :match :path-params :user-id)]
    (-> state
        (assoc :db-queries (model/select-user-team user-id))
        (assoc :view view/users))))

(defn select-team-users [state]
  (let [team-id (-> state :request-data :match :path-params :team-id)]
    (-> state
        (assoc :db-queries (model/select-team-users team-id))
        (assoc :view view/users))))
