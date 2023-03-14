(ns xiana-experiment-flexiana.pages.support.view
  (:require
   [xiana-experiment-flexiana.pages.support.routing]
   [xiana-experiment-flexiana.routing.core :as routing :refer [url-for]]
   [xiana-experiment-flexiana.pages.support.events :as support-events]
   [xiana-experiment-flexiana.events.tickets :as events-tickets]
   [xiana-experiment-flexiana.pages.support.subs :as support-subs]
   [xiana-experiment-flexiana.subs.tickets :as subs-tickets]
   [xiana-experiment-flexiana.subs.teams :as subs-teams]
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [re-frame.core :as rf]))

(defn ticket-form []
  (let [ticket-name @(rf/subscribe [::support-subs/ticket-name-input])]
    [:<>
     [:span {:class "text-xl font-bold"}
      "Support"]
     [:div {:class "py-1 max-w-max flex gap-2"}
      [tc/basic-field-input {:type "text"
                             :placeholder "Ticket name..."
                             :value ticket-name
                             :on-change #(rf/dispatch [::support-events/ticket-name-input (-> % .-target .-value)])}]
      [tc/primary-button {:content "Create new ticket"
                          :on-click #(when (seq ticket-name)
                                       (rf/dispatch [::events-tickets/insert-ticket ticket-name])
                                       (rf/dispatch [::support-events/ticket-name-input nil]))}]]]))

(defn tickets-table []
  (let [team-tickets @(rf/subscribe [::subs-tickets/select-all-tickets])]
    [:<>
     [:span {:class "text-xl font-bold"}
      "Support"]
     [tc/data-table
      {:titles ["Ticket name" "Messages" "Actions"]
       :items (for [t team-tickets]
                ^{:key (random-uuid)}
                (let [ticket-messages-number @(rf/subscribe [::subs-teams/ticket-messages-number (:id t)])]
                  [(str (:name t) (if (:resolved t) " (resolved)" " (unresolved)"))
                   ticket-messages-number
                   [tc/primary-button
                    {:content "Open"
                     :on-click #(rf/dispatch [:navigate (url-for :ticket-chat
                                                                 :ticket-id (:id t))])}]]))}]]))

(defn page []
  [:div {:class "p-6"}
   [ticket-form]
   [tickets-table]])

(defmethod routing/resolve-view :tickets [_] [page])
