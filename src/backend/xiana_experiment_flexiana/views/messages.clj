(ns xiana-experiment-flexiana.views.messages
  (:require
   [xiana-experiment-flexiana.views.common :as views]))

(defn messages [{{db-results :db-data} :response-data :as state}]
  (views/response state {:data {:messages (first db-results)}}))
