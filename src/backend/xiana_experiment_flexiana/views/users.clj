(ns xiana-experiment-flexiana.views.users
  (:require
   [xiana-experiment-flexiana.views.common :as views]))

(defn users [{{db-results :db-data} :response-data :as state}]
  (let [wo-password (map #(dissoc % :password) (first db-results))] 
    (views/response state {:data {:users wo-password}})))

(defn insert-user-by-themselve [{{db-results :db-data} :response-data :as state}]
  (let [[user-query team-query team-user-query] db-results]
    (views/response
     state
     {:data {:user-data (-> user-query
                            first
                            (dissoc :password)
                            (dissoc :api-token))
             :team-data (-> team-user-query
                            first
                            (dissoc :user-id)
                            (assoc :name (-> team-query first :name)))}})))

(defn login [{{db-results :db-data} :response-data :as state}]
  (let [[user-query team-user-query] db-results]
    (views/response
     state
     {:data {:user-data (-> user-query
                            first
                            (dissoc :password)
                            (dissoc :api-token))
             :team-data (-> team-user-query
                            first
                            (dissoc :user-id))}})))
