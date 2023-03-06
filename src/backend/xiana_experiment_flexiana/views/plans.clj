(ns xiana-experiment-flexiana.views.plans
  (:require
   [xiana-experiment-flexiana.views.common :as views]))

(defn plans [{{db-results :db-data} :response-data :as state}]
  (views/response state {:data {:plans (first db-results)}}))
