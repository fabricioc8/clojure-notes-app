(ns xiana-experiment-flexiana.views.subscriptions
  (:require
   [xiana-experiment-flexiana.views.common :as views]))

(defn subscriptions [{{db-results :db-data} :response-data :as state}]
  (views/response state {:data {:subscriptions (first db-results)}}))
