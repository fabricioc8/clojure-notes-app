(ns xiana-experiment-flexiana.views.tickets
  (:require
   [xiana-experiment-flexiana.views.common :as views]))

(defn tickets [{{db-results :db-data} :response-data :as state}]
  (views/response state {:data {:tickets (first db-results)}}))
