(ns xiana-experiment-flexiana.views.content-pages
  (:require
   [xiana-experiment-flexiana.views.common :as views]))

(defn content-pages [{{db-results :db-data} :response-data :as state}]
  (views/response state {:data {:content-pages (first db-results)}}))
