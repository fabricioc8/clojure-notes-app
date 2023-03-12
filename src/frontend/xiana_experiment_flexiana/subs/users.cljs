(ns xiana-experiment-flexiana.subs.users
  (:require
   [xiana-experiment-flexiana.subs.app-db :as subs-db]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::session-user-id
 :<- [::subs-db/session]
 (fn [session _]
   (get-in session [:user :user-id])))

(rf/reg-sub
 ::session-user-data
 :<- [::subs-db/session]
 (fn [session _]
   (get session :user-data)))

(rf/reg-sub
 ::session-team-team
 :<- [::subs-db/session]
 (fn [session _]
   (get session :team-data)))

(rf/reg-sub
 ::user-team
 :<- [::subs-db/session]
 (fn [session _]
   (get session :team)))

(rf/reg-sub
 ::team-users
 :<- [::subs-db/entity]
 (fn [entity _]
   (get entity :team-users)))
