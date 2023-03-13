(ns xiana-experiment-flexiana.subs.teams
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.subs.app-db :as subs-db]))

(rf/reg-sub
 ::team-users
 :<- [::subs-db/entity]
 (fn [entity _]
   (get entity :team-users)))
