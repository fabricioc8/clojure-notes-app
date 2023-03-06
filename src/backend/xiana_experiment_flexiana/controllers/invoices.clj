(ns xiana-experiment-flexiana.controllers.invoices
  (:require
   [xiana-experiment-flexiana.models.invoices :as model]
   [xiana-experiment-flexiana.views.invoices :as view]))

(defn insert-invoice [{{params :body-params} :request :as state}]
  (-> state
      (assoc :db-queries (model/insert-invoice params))
      (assoc :view view/invoices)))

(defn select-all-invoices [state]
  (-> state
      (assoc :db-queries (model/select-all-invoices))
      (assoc :view view/invoices)))

(defn select-team-invoices [state]
  (let [team-id (-> state :request-data :match :path-params :team-id)]
    (-> state
        (assoc :db-queries (model/select-team-invoices team-id))
        (assoc :view view/invoices))))
