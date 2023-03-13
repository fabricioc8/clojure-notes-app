(ns xiana-experiment-flexiana.subs.ticket-messages
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.subs.tickets :as subs-tickets]
   [xiana-experiment-flexiana.util.seq :as util]
   [xiana-experiment-flexiana.subs.teams :as subs-teams]
   [xiana-experiment-flexiana.subs.app-db :as subs-db]))

(rf/reg-sub
 ::ticket-messages
 :<- [::subs-db/entity]
 (fn [entity _]
   (get-in entity [:current-ticket :ticket-messages])))

(rf/reg-sub
 ::ticket
 :<- [::subs-db/entity]
 :<- [::subs-tickets/select-team-tickets]
 (fn [[entity tickets] _]
   (let [ticket-id (get-in entity [:current-ticket :ticket-id])]
     (->> tickets
          (filter #(= (:id %) ticket-id))
          first))))

(rf/reg-sub
 ::message-author
 :<- [::subs-teams/team-users]
 (fn [team-users [_ user-id]]
   (->> team-users
        (util/seek #(= (:id %) user-id))
        :email)))
