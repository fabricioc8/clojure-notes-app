(ns xiana-experiment-flexiana.admin-pages.admin-teams.subs
  (:require
   [xiana-experiment-flexiana.subs.app-db :as db]
   [xiana-experiment-flexiana.subs.notes :as subs-notes]
   [xiana-experiment-flexiana.subs.users :as subs-users]
   [xiana-experiment-flexiana.subs.teams :as subs-teams]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::admin-table
 :<- [::subs-users/teams-users]
 :<- [::subs-teams/all-teams]
 (fn [[users teams] _]
   (map (fn [t]
          (assoc t :members (count (filter (fn [u]
                                             (= (:team-id u) (:id t)))
                                           users))))
        teams)))
