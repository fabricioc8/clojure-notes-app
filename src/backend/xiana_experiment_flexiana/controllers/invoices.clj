(ns backend.xiana-experiment-flexiana.controllers.invoices
  (:require
   [backend.xiana-experiment-flexiana.models.invoices :as inv-mod]))

(defn insert-invoice [{{params :body-params} :request :as state}]
  (-> state
      (assoc :db-queries (inv-mod/insert-invoice params))))

(defn select-all-invoices [state]
  (-> state
      (assoc :db-queries (inv-mod/select-all-invoices))))

(defn select-team-invoices [state]
  (let [team-id (-> state :request-data :match :path-params :id)]
    (-> state
        (assoc :db-queries (inv-mod/select-team-invoices team-id)))))
