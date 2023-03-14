(ns xiana-experiment-flexiana.subs.teams
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.subs.app-db :as subs-db]))

(rf/reg-sub
 ::team-users
 :<- [::subs-db/entity]
 (fn [entity _]
   (get entity :team-users)))

(rf/reg-sub
 ::team-tickets-messages
 :<- [::subs-db/entity]
 (fn [entity _]
   (get entity :team-tickets-messages)))

(rf/reg-sub
 ::ticket-messages-number
 :<- [::team-tickets-messages]
 (fn [team-tickets-messages [_ ticket-id]]
   (->> team-tickets-messages
        (filter #(= (:ticket-id %) ticket-id))
        count)))

(rf/reg-sub
 ::all-teams
 :<- [::subs-db/entity]
 (fn [entity _]
   (get entity :teams)))

(rf/reg-sub
 ::team-name-by-id
 :<- [::all-teams]
 (fn [all-teams [_ team-id]]
   (->> all-teams
        (filter #(= (:id %) team-id))
        first
        :name)))
