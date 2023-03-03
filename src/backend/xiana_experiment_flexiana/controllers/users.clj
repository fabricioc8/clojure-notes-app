(ns backend.xiana-experiment-flexiana.controllers.users
  (:require
   [backend.xiana-experiment-flexiana.models.users :as us-mod]))

(defn insert-user-by-themselve [{{params :body-params} :request :as state}]
  (-> state
      (assoc :db-queries (us-mod/insert-user-by-themselve params))))

(defn insert-user-by-invitation [{{params :body-params} :request :as state}]
  (-> state
      (assoc :db-queries (us-mod/insert-user-by-invitation params))))

(defn select-user[state]
  (let [user-id (-> state :request-data :match :path-params :id )]
    (-> state
      (assoc :db-queries (us-mod/select-user user-id)))))

(defn select-all-users [state]
  (-> state
      (assoc :db-queries (us-mod/select-all-users))))

(defn select-team-users [state] 
  (let [team-id (-> state :request-data :match :path-params :id )]
    (-> state
      (assoc :db-queries (us-mod/select-team-users team-id)))))

(defn update-user [{{params :body-params} :request :as state}] 
  (let [user-id (-> state :request-data :match :path-params :id )]
    (-> state
      (assoc :db-queries (us-mod/update-user user-id params)))))
