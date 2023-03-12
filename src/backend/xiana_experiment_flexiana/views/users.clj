(ns xiana-experiment-flexiana.views.users
  (:require
   [xiana-experiment-flexiana.views.common :as views]))

(defn users [{{db-results :db-data} :response-data :as state}]
  (let [[user-query _ team-user-query] db-results]
    (views/response
     state
     {:data {:user-data (-> user-query
                            first
                            (dissoc :password)
                            (dissoc :api-token))
             :team-data (-> team-user-query
                            first
                            (dissoc :user-id))}})))
