(ns xiana-experiment-flexiana.models.tickets
  (:require
   [xiana-experiment-flexiana.models.common :refer [->UUID next-uuid] :as mc]
   [honeysql.core :as sql]
   [honeysql.helpers :as sqlh]))

(defn insert-ticket [params]
  (let [new-ticket-id (next-uuid)
        ticket-params (-> params
                          (assoc :id new-ticket-id)
                          mc/->ticket)]
    {:queries [{:insert-into :tickets
                :values [ticket-params]}]}))

(defn select-team-tickets [team-id]
  {:queries [{:select [:*]
              :from [:tickets]
              :where [:= :team-id (->UUID team-id)]}]})

(defn select-all-tickets []
  {:queries [{:select [:*]
              :from [:tickets]}]})

(defn update-ticket [ticket-id params]
  {:queries [{:update :tickets
              :set (mc/->ticket params)
              :where [:= :id (->UUID ticket-id)]}]})
