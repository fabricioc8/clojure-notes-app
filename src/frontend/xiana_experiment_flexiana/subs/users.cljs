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
 :<- [::subs-db/entity]
 (fn [entity _]
   (:user-team entity)))

(rf/reg-sub
 ::all-users
 :<- [::subs-db/entity]
 (fn [entity _]
   (get entity :users)))
