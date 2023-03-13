(ns xiana-experiment-flexiana.controllers.users
  (:require
   [xiana-experiment-flexiana.models.users :as model]
   [xiana-experiment-flexiana.views.users :as view]
   [xiana-experiment-flexiana.controller-behaviors.login :as cbl]))

(defn insert-user-by-themselve [{{params :body-params} :request :as state}]
  (assoc state
         :db-queries (model/insert-user-by-themselve params)
         :view view/insert-user-by-themselve
         :side-effect cbl/generate-token))

(defn insert-user-by-invitation [{{params :body-params} :request :as state}]
  (assoc state
         :db-queries (model/insert-user-by-invitation params)
         :view view/users))

(defn login [{{params :body-params} :request :as state}]
  (assoc state
         :db-queries (model/login params)
         :view view/login
         :side-effect cbl/valid-login?))

(defn select-user [state]
  (let [user-id (-> state :request-data :match :path-params :user-id)]
    (assoc state
           :db-queries (model/select-user user-id)
           :view view/users)))

(defn select-all-users [state]
  (assoc state
         :db-queries (model/select-all-users)
         :view view/users))

(defn update-user [{{params :body-params} :request :as state}] 
  (let [user-id (-> state :request-data :match :path-params :user-id)]
    (assoc state
           :db-queries (model/update-user user-id params)
           :view view/users)))

(defn select-user-team [state]
  (let [user-id (-> state :request-data :match :path-params :user-id)]
    (assoc state
           :db-queries (model/select-user-team user-id)
           :view view/users)))

(defn select-team-users [state]
  (let [team-id (-> state :request-data :match :path-params :team-id)]
    (assoc state
           :db-queries (model/select-team-users team-id)
           :view view/users)))
