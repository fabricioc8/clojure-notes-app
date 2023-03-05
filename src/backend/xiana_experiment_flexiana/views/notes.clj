(ns backend.xiana-experiment-flexiana.views.notes
  (:require
   [xiana-experiment-flexiana.views.common :as views]))

(defn notes [{{db-results :db-data} :response-data :as state}]
  (views/response state {:data {:notes (first db-results)}}))
