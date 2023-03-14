(ns xiana-experiment-flexiana.admin-pages.admin-billing.view
  (:require
   [xiana-experiment-flexiana.admin-pages.admin-billing.routing]
   [xiana-experiment-flexiana.routing.core :as routing :refer [url-for]]
   [xiana-experiment-flexiana.subs.subscriptions :as subs-subscriptions]
   [xiana-experiment-flexiana.subs.invoices :as subs-invoices]
   [xiana-experiment-flexiana.admin-pages.admin-billing.subs :as view-subs]
   [xiana-experiment-flexiana.admin-pages.admin-teams.subs :as admin-teams-subs]
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [re-frame.core :as rf]))

(defn subscriptions-table []
  (let [teams @(rf/subscribe [::admin-teams-subs/admin-teams-table])]
    [tc/data-table
     {:titles ["Team" "Plan" "Actions"]
      :items (for [t teams]
               ^{:key (random-uuid)}
               [(:name t)
                (:plan t)
                [tc/primary-button
                 {:content "Cancel"
                  :on-click #(rf/dispatch [:navigate (url-for :ticket-chat
                                                              :ticket-id (:id t))])}]])}]))
(defn invoices-table []
  (let [invoices @(rf/subscribe [::view-subs/invoices-for-billing])]
    [tc/data-table
     {:titles ["Team" "Plan" "Name" "Month" "Amount USD" "Actions"]
      :items (for [t invoices]
               ^{:key (random-uuid)}
               [(:team t)
                (:plan t)
                (:team-id t)
                (:month t)
                (:amount-usd t)
                [tc/primary-button
                 {:content "Cancel"
                  :on-click #(rf/dispatch [:navigate (url-for :ticket-chat
                                                              :ticket-id (:id t))])}]])}]))

(defn page []
  [:div {:class "p-6"}
   [:span {:class "text-xl font-bold"}
    "Billing"]
   [:span {:class "text-xl font-bold"}
    "Active subscriptions"]
   [subscriptions-table]
   [:span {:class "text-xl font-bold"}
    "Invoices"]
   [invoices-table]])

(defmethod routing/resolve-view :admin-billing [_] [page])
