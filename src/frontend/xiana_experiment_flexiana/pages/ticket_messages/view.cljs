(ns xiana-experiment-flexiana.pages.ticket-messages.view
  (:require
   [xiana-experiment-flexiana.pages.support.routing]
   [xiana-experiment-flexiana.pages.ticket-messages.routing]
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.subs.ticket-messages :as subs-ticket-messages]
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [re-frame.core :as rf]))

(defn messages-list []
  (let [ticket-messages @(rf/subscribe [::subs-ticket-messages/ticket-messages])]
    [:div
     (for [t ticket-messages]
       (let [rid (random-uuid)
             author (or @(rf/subscribe [::subs-ticket-messages/message-author (:user-id t)])
                        "Admin")]
         ^{:key rid}
         [tc/items-list {:user-name author
                         :message (:message t)
                         :li-key rid
                         :icon "icon"}]))]))

(defn page []
  (let [ticket @(rf/subscribe [::subs-ticket-messages/ticket])]
    [:div {:class "p-6"}
     [:span {:class "text-xl font-bold"}
      "Ticket: "]
     [:span {:class "text-xl font-bold"}
      (:name ticket)]
     [:span {:class "text-xl font-bold"}
      (if (:resolved ticket)
        " (Resolved)"
        " (Unresolved)")]
     [messages-list]]))

(defmethod routing/resolve-view :ticket-chat [_] [page])
