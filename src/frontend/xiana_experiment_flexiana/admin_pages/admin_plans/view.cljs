(ns xiana-experiment-flexiana.admin-pages.admin-plans.view
  (:require
   [xiana-experiment-flexiana.admin-pages.admin-plans.routing]
   [xiana-experiment-flexiana.routing.core :as routing :refer [url-for]]
   [xiana-experiment-flexiana.events.plans :as events-plans]
   [xiana-experiment-flexiana.subs.plans :as subs-plans]
   [xiana-experiment-flexiana.subs.teams :as subs-teams]
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [reagent.core :as r]
   [re-frame.core :as rf]))

(defn plans-table []
  (let [plans @(rf/subscribe [::subs-plans/all-plans])]
    [tc/data-table
     {:titles ["Name" "Price" "Per user" "Notes" "Chars" "Members" "Team ID" "Actions"]
      :items (for [p plans]
               ^{:key (random-uuid)}
               [(:name p)
                (:price-usd p)
                (:price-per-user p)
                (:max-notes p)
                (:max-chars p)
                (:max-users p)
                (:id p)
                [tc/primary-button
                 {:content "Edit"
                  :on-click #()#_#(rf/dispatch [:navigate (url-for :ticket-chat
                                                              :ticket-id (:id p))])}]])}]))

(defn valid-plan? [plan]
  (reduce (fn [acc k]
            (if acc
              (if (string? (k plan))
                (seq (k plan))
                (boolean (k plan)))
              acc))
          true
          [:name :max-notes :max-chars :max-users :team-id :price-usd]))

(defn new-plan-form []
  (r/with-let [state (r/atom {:price-per-user false})]
    (let [teams @(rf/subscribe [::subs-teams/all-teams])
          team-options (map (fn [t] {:label (:name t)
                                     :value (:id t)})
                            teams)]
      [:div {:class "flex"}
       [:div {:class "flex flex-col"}
        [tc/basic-field-input
         {:type "text"
          :name "plan-name"
          :placeholder "Plan name"
          :value (:name @state)
          :on-change #(swap! state assoc :name (-> % .-target .-value))}]
        [tc/basic-field-input
         {:type "number"
          :name "max-notes"
          :placeholder "Max notes"
          :value (:max-notes @state)
          :on-change #(swap! state assoc :max-notes (-> % .-target .-value js/parseInt))}]
        [tc/selector
         {:name   "team-selector"
          :default-value nil
          :options (into [{:label "Select team"
                           :value nil}]
                         team-options)
          :on-change #(swap! state assoc :team-id (-> % .-target .-value))}]]
       [:div {:class "flex flex-col"}
        [tc/basic-field-input
         {:type "number"
          :name "price-usd"
          :placeholder "Price usd"
          :value (:price-usd @state)
          :on-change #(swap! state assoc :price-usd (-> % .-target .-value js/parseInt))}]
        [tc/basic-field-input
         {:type "number"
          :name "max-chars"
          :placeholder "Max chars"
          :value (:max-chars @state)
          :on-change #(swap! state assoc :max-chars (-> % .-target .-value js/parseInt))}]
        [tc/primary-button
         {:content "Create plan"
          :disabled (not (valid-plan? @state))
          :on-click #(when (valid-plan? @state)
                       (rf/dispatch [::events-plans/insert-new-plan @state]))}]]
       [:div {:class "flex flex-col"}
        [tc/basic-field-input
         {:type "number"
          :name "max-users"
          :placeholder "Max users"
          :value (:max-users @state)
          :on-change #(swap! state assoc :max-users (-> % .-target .-value js/parseInt))}]]])))

(defn page []
  [:div {:class "p-6"}
   [:span {:class "text-xl font-bold"}
    "Plans"]
   [plans-table]
   [:span {:class "text-xl font-bold"}
    "Create new plan"]
   [new-plan-form]])

(defmethod routing/resolve-view :admin-plans [_] [page])
