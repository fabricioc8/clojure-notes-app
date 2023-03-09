(ns xiana-experiment-flexiana.pages.billing.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.pages.billing.routing]
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [xiana-experiment-flexiana.subs.plans :as subs-plans]
   [xiana-experiment-flexiana.subs.invoices :as subs-invoices]
   [xiana-experiment-flexiana.subs.subscriptions :as subs-subscriptions]
   [xiana-experiment-flexiana.util.seq :as util]
   [re-frame.core :as rf]))

(defn team-plans-table []
  (let [team-plans @(rf/subscribe [::subs-plans/team-plans])
        team-plans-enterpise (into team-plans [{:name      "Enterpise"
                                                :price-usd nil
                                                :max-notes nil
                                                :max-chars nil
                                                :max-users nil}])
        current-plan @(rf/subscribe [::subs-subscriptions/select-current-subscription])
        current-plan-name @(rf/subscribe [::subs-plans/team-plan-name])]
    [:div
     [:span "Your team is currently on: "]
     [:span {:class "font-bold"};check
      current-plan-name]
     [:span " plan"]
     [tc/data-table {:titles ["Name" "Price" "Max notes" "Char limit" "Max members" "Actions"]
                     :items (for [p team-plans-enterpise]
                              ^{:key (random-uuid)}
                              (conj (util/sort-map-vals p [:name :price-usd :max-notes :max-chars :max-users])
                                    (cond
                                      (= (:id p) (:plan-id current-plan)) "Active plan"
                                      (= (:name p) "Enterpise") [tc/primary-button {:content "Contact us"
                                                                                    :on-click #()}]
                                      :else [tc/primary-button {:content "Upgrade"
                                                                :on-click #()}])))}]]))

(defn invoices-table []
  (let [team-invoices @(rf/subscribe [::subs-invoices/team-invoices])
        team-invoices-detail (mapv (fn [inv]
                                     (assoc inv
                                            :details [tc/primary-button
                                                      {:content "Open/Download"
                                                       :on-click #()}]))
                                   team-invoices)]
    [tc/data-table {:titles ["Name" "Month" "Amount USD" "Details"]
                    :items (for [i team-invoices-detail]
                             ^{:key (random-uuid)}
                             (util/sort-map-vals i [:name :month :amount-usd :details]))}]))

(defn page []
  [:div {:class "p-6"}
   [:span {:class "text-xl font-bold"}
    "Billing"]
   [team-plans-table]
   [:span {:class "text-xl font-bold"}
    "Invoices"]
   [invoices-table]])

(defmethod routing/resolve-view :billing [_] [page])
