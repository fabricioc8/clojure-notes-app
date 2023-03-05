(ns backend.xiana-experiment-flexiana.views.users
  (:require
   [xiana-experiment-flexiana.views.common :as views]))

(defn users [{{db-results :db-data} :response-data :as state}]
  (views/response state {:data {:users (first db-results)}}))
