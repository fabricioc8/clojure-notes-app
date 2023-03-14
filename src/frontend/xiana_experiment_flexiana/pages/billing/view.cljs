(ns xiana-experiment-flexiana.pages.billing.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.pages.billing.routing]
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [xiana-experiment-flexiana.pages.billing.events :as view-events]
   [xiana-experiment-flexiana.events.subscriptions :as events-subscriptions]
   [xiana-experiment-flexiana.events.billing-details :as events-billings]
   [xiana-experiment-flexiana.subs.plans :as subs-plans]
   [xiana-experiment-flexiana.subs.invoices :as subs-invoices]
   [xiana-experiment-flexiana.pages.billing.subs :as view-subs]
   [xiana-experiment-flexiana.subs.billing-details :as subs-billing]
   [xiana-experiment-flexiana.util.seq :as util]
   [re-frame.core :as rf]))

(defn team-plans-table []
  (let [team-plans @(rf/subscribe [::subs-plans/team-plans])
        team-plans-enterpise (into team-plans [{:name      "Enterpise"
                                                :price-usd nil
                                                :max-notes nil
                                                :max-chars nil
                                                :max-users nil}])
        current-plan @(rf/subscribe [::subs-plans/current-team-plan])]
    [:div
     [:span "Your team is currently on: "]
     [:span {:class "font-bold"};check
      (:name current-plan)]
     [:span " plan"]
     [tc/data-table
      {:titles ["Name" "Price" "Max notes" "Char limit" "Max members" "Actions"]
       :items (for [p team-plans-enterpise]
                ^{:key (random-uuid)}
                (conj (util/sort-map-vals p [:name :price-usd :max-notes :max-chars :max-users])
                      (cond
                        (= (:id p) (:id current-plan)) "Active plan"
                        (= (:name p) "Enterpise") [tc/primary-button {:content "Contact us"
                                                                      :on-click #()}]
                        :else [tc/primary-button {:content "Upgrade"
                                                  :on-click #(rf/dispatch [::events-subscriptions/insert-subscription
                                                                           (:id p)])}])))}]]))

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

(defn billing-form [billing-data]
  (let [new-billing-data @(rf/subscribe [::view-subs/billing-inputs-values])]
    [:<>
     [:span {:class "text-xl font-bold"}
      "Billing details"]
     [:div {:class "flex"}
      [tc/text-area {:width "w-64"
                     :name "address-detail"
                     :placeholder "Address"
                     :value (or (:address new-billing-data) (:address billing-data))
                     :on-change #(rf/dispatch [::view-events/billing-inputs-values
                                               :address (-> % .-target .-value)])}]
      [:div {:class "max-w-max"}
       [tc/basic-field-input {:type "text"
                              :name "vat-input"
                              :placeholder "VAT"
                              :value (or (:vat new-billing-data) (:vat billing-data))
                              :on-change #(rf/dispatch [::view-events/billing-inputs-values
                                                        :vat (-> % .-target .-value)])}]]
      [:div {:class "max-w-max"}
       [tc/basic-field-input {:type "text"
                              :name "company-name"
                              :placeholder "Company name"
                              :value (or (:company-name new-billing-data) (:company-name billing-data))
                              :on-change #(rf/dispatch [::view-events/billing-inputs-values
                                                        :company-name (-> % .-target .-value)])}]
       [tc/primary-button
        {:content "Save billing info"
         :on-click #(do (if billing-data
                          (rf/dispatch [::events-billings/update-team-billing-details new-billing-data])
                          (rf/dispatch [::events-billings/insert-team-billing-details new-billing-data]))
                        (rf/dispatch [::view-events/reset-billing-input-values]))}]]]]))

(defn page []
  (let [billing-data @(rf/subscribe [::subs-billing/select-billing-details])]
    [:div {:class "p-6"}
     [:span {:class "text-xl font-bold"}
      "Billing"]
     [team-plans-table]
     [:span {:class "text-xl font-bold"}
      "Invoices"]
     [invoices-table]
     [billing-form billing-data]]))

(defmethod routing/resolve-view :billing [_] [page])
