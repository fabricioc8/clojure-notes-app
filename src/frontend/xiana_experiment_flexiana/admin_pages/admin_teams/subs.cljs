(ns xiana-experiment-flexiana.admin-pages.admin-teams.subs
  (:require
   [xiana-experiment-flexiana.subs.subscriptions :as subs-subscriptions]
   [xiana-experiment-flexiana.subs.notes :as subs-notes]
   [xiana-experiment-flexiana.subs.users :as subs-users]
   [xiana-experiment-flexiana.subs.teams :as subs-teams]
   [xiana-experiment-flexiana.subs.plans :as subs-plans]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::admin-teams-table
 :<- [::subs-users/teams-users]
 :<- [::subs-teams/all-teams]
 :<- [::subs-notes/all-notes]
 :<- [::subs-subscriptions/active-subscriptions]
 :<- [::subs-plans/all-plans]
 (fn [[users teams notes subscriptions plans] _]
   (map (fn [t]
          (let [plan-id (->> subscriptions
                             (filter (fn [s] (= (:team-id s) (:id t))))
                             first
                             :plan-id)
                plan (->> plans
                          (filter (fn [p] (= (:id p) plan-id)))
                          first)]
            (assoc t
                   :members (count
                             (filter (fn [u]
                                       (= (:team-id u) (:id t)))
                                     users))
                   :existing-notes (count
                                    (filter (fn [n]
                                              (= (:team-id n) (:id t)))
                                            notes))
                   :max-notes (:max-notes plan)
                   :plan (:name plan)
                   :price-usd (:price-usd plan))))
        teams)))
