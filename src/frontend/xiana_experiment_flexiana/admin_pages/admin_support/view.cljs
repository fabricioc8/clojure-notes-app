(ns xiana-experiment-flexiana.admin-pages.admin-support.view
  (:require
   [xiana-experiment-flexiana.admin-pages.admin-support.routing]
   [xiana-experiment-flexiana.routing.core :as routing :refer [url-for]]
   [xiana-experiment-flexiana.subs.tickets :as subs-tickets]
   [xiana-experiment-flexiana.subs.teams :as subs-teams]
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [re-frame.core :as rf]))

(defn unresolved-table [unresolved-tickets]
  [tc/data-table
   {:titles ["Ticket name" "Team" "Actions"]
    :items (for [t unresolved-tickets]
             ^{:key (random-uuid)}
             (let [team-name @(rf/subscribe [::subs-teams/team-name-by-id (:team-id t)])]
               [(:name t)
                team-name
                [tc/primary-button
                 {:content "Open details"
                  :on-click #(rf/dispatch [:navigate (url-for :ticket-chat
                                                              :ticket-id (:id t))])}]]))}])

(defn resolved-table [resolved-tickets]
  [tc/data-table
   {:titles ["Ticket name" "Team" "Actions"]
    :items (for [t resolved-tickets]
             ^{:key (random-uuid)}
             (let [team-name @(rf/subscribe [::subs-teams/team-name-by-id (:team-id t)])]
               [(:name t)
                team-name
                [tc/primary-button
                 {:content "Open details"
                  :on-click #(rf/dispatch [:navigate (url-for :ticket-chat
                                                              :ticket-id (:id t))])}]]))}])

(defn page []
  (let [all-tickets @(rf/subscribe [::subs-tickets/select-all-tickets])
        resolved-tickets (filter :resolved all-tickets)
        unresolved-tickets (filter #(not (:resolved %)) all-tickets)]
    [:div {:class "p-6"}
     [:span {:class "text-xl font-bold"}
      "Support"]
     [:div
      [:span {:class "text-xl font-bold"}
       "Unresolved"]
      [unresolved-table unresolved-tickets]]
     [:div
      [:span {:class "text-xl font-bold"}
       "Resolved"]
      [resolved-table resolved-tickets]]]))

(defmethod routing/resolve-view :admin-tickets [_] [page])
