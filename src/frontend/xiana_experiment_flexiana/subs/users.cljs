(ns xiana-experiment-flexiana.subs.users
  (:require
   [xiana-experiment-flexiana.subs.app-db :as subs-db]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::session-user-data
 :<- [::subs-db/session]
 (fn [session _]
   (get session :user-data)))

(rf/reg-sub
 ::session-team-data
 :<- [::subs-db/session]
 (fn [session _]
   (get session :team-data)))

(rf/reg-sub
 ::user-team
 :<- [::subs-db/session]
 (fn [session _]
   (:team-data session)))

(rf/reg-sub
 ::all-users
 :<- [::subs-db/entity]
 (fn [entity _]
   (get entity :users)))

(rf/reg-sub
 ::teams-users
 :<- [::subs-db/entity]
 (fn [entity _]
   (get entity :teams-users)))
