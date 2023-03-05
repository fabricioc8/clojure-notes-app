(ns backend.xiana-experiment-flexiana.views.teams
  (:require
   [xiana-experiment-flexiana.views.common :as views]))

(defn teams [{{db-results :db-data} :response-data :as state}]
  (views/response state {:data {:teams (first db-results)}}))
