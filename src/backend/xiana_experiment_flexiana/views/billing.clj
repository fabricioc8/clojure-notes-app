(ns xiana-experiment-flexiana.views.billing
  (:require
   [xiana-experiment-flexiana.views.common :as views]))

(defn billing-details [{{db-results :db-data} :response-data :as state}]
  (views/response state {:data {:billing-details (first db-results)}}))
