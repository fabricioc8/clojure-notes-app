(ns xiana-experiment-flexiana.pages.ticket-messages.view
  (:require
   [xiana-experiment-flexiana.pages.support.routing]
   [xiana-experiment-flexiana.pages.ticket-messages.routing] 
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.events.ticket-messages :as events-ticket-messages]
   [xiana-experiment-flexiana.subs.ticket-messages :as subs-ticket-messages]
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [reagent.core :as r]
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
;;hacer una subscripcion mas copada para tener en los ticket-messages el nombre del usuario en vez de user-id

(defn message-text-area [ticket]
  (r/with-let [message-content (r/atom "")]
    [:div
     [tc/text-area
       {:width "w-1/2"
        :name "message-content"
        :placeholder "Message content..."
        :default-value ""
      ;:max-lenght (str max-chars)
        :value @message-content
        :on-change #(reset! message-content (-> % .-target .-value))}]
     [tc/primary-button {:content "Send message"
                         :on-click #(when (seq @message-content)
                                      (rf/dispatch [::events-ticket-messages/insert-ticket-message
                                                    (:id ticket) @message-content])
                                      (reset! message-content ""))}]]))

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
     [messages-list]
     [message-text-area ticket]
    ;;  [tc/primary-button {:content "Mark as resolved"
    ;;                      :on-click #(when (seq note-title)
    ;;                                   (rf/dispatch [::event-notes/insert-new-note note-title (:team-id team-data)]))}]
     ]))

(defmethod routing/resolve-view :ticket-chat [_] [page])
