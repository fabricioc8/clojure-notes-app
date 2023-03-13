(ns xiana-experiment-flexiana.models.messages
  (:require
   [xiana-experiment-flexiana.models.common :refer [->UUID next-uuid] :as mc]
   [honeysql.core :as sql]
   [honeysql.helpers :as sqlh]))

(defn insert-ticket-message [params]
  (let [new-message-id (next-uuid)
        message-params (-> params
                           (assoc :id new-message-id)
                           (mc/->message))]
    {:queries [{:insert-into :ticket-messages
                :values [message-params]}]}))

(defn select-ticket-messages [ticket-id]
  {:queries [{:select [:*]
              :from [:ticket-messages]
              :where [:= :ticket-id (->UUID ticket-id)]}]})

(defn select-team-tickets-messages [team-id]
  {:queries [{:select [:*]
              :from [:ticket-messages]
              :where [:in :ticket-id {:select [:id]
                                      :from [:tickets]
                                      :where [:= :team-id (->UUID team-id)]}]}]})
