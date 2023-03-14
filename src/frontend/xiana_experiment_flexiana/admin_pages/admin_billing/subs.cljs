(ns xiana-experiment-flexiana.admin-pages.admin-billing.subs
  (:require
   [xiana-experiment-flexiana.subs.invoices :as subs-invoices]
   [xiana-experiment-flexiana.admin-pages.admin-teams.subs :as admin-teams-subs]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::invoices-for-billing
 :<- [::subs-invoices/all-invoices]
 :<- [::admin-teams-subs/admin-teams-table]
 (fn [[invoices team-info] _]
   (map (fn [i]
          (let [team (first (filter #(= (:id %) (:team-id i)) team-info))]
            (assoc i
                   :team (:name team)
                   :plan (:plan team)
                   :month "-")))
    invoices)))
