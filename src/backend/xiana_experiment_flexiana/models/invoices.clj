(ns backend.xiana-experiment-flexiana.models.invoices
  (:require
   [backend.xiana-experiment-flexiana.models.common :refer [->UUID next-uuid] :as mc]
   [honeysql.core :as sql]
   [honeysql.helpers :as sqlh]))

(defn insert-invoice [params]
  (let [new-invoice-id (next-uuid)
        invoice-params (-> params
                           (assoc :id new-invoice-id)
                           mc/->invoice)]
    {:queries [{:insert-into :invoices
                :values [invoice-params]}]}))

(defn select-all-invoices []
  {:queries [{:select [:*]
              :from [:invoices]}]})

(defn select-team-invoices [team-id]
  {:queries [{:select [:*]
              :from [:invoices]
              :where [:= :team-id (->UUID team-id)]}]})
