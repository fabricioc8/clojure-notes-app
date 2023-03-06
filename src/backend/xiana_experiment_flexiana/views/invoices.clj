(ns xiana-experiment-flexiana.views.invoices
  (:require
   [xiana-experiment-flexiana.views.common :as views]))

(defn invoices [{{db-results :db-data} :response-data :as state}]
  (views/response state {:data {:invoices (first db-results)}}))
