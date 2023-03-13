(ns xiana-experiment-flexiana.events.ticket-messages
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]))

(rf/reg-event-db
 ::ticket-messages-selected
 (fn [db [_ ticket-id response]]
   (let [ticket-messages (-> response :data :messages)]
     (assoc-in db [:entity :current-ticket] {:ticket-id ticket-id
                                             :ticket-messages ticket-messages}))))

(rf/reg-event-fx
 ::select-ticket-messages
 (fn [_ [_ ticket-id]]
   {:http-xhrio {:uri (util/url "/api/ticket-messages/" ticket-id)
                 :method :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::ticket-messages-selected ticket-id]
                 ;:on-failure [::http/http-error]
                 }}))

(rf/reg-event-db
 ::team-tickets-messages-selected
 (fn [db [_ response]]
   (let [team-ticket-messages (-> response :data :messages)]
     (assoc-in db [:entity :team-tickets-messages] team-ticket-messages))))

(rf/reg-event-fx
 ::select-team-tickets-messages
 (fn [{:keys [db]} [_]]
   (let [team-id (-> db :session :team-data :team-id)]
     {:http-xhrio {:uri (util/url "/api/team-tickets-messages/" team-id)
                   :method :get
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::team-tickets-messages-selected]
                 ;:on-failure [::http/http-error]
                   }})))

(rf/reg-event-db
 ::ticket-message-inserted
 (fn [db [_ response]]
   (let [ticket-message (-> response :data :messages first)]
     (update-in db [:entity :current-ticket :ticket-messages] conj ticket-message))))

(rf/reg-event-fx
 ::insert-ticket-message
 (fn [{:keys [db]} [_ ticket-id message]]
   (let [user-id (-> db :session :user-data :id)]
     {:http-xhrio {:uri "/api/ticket-messages"
                   :method :post
                   :params {:ticket-id ticket-id
                            :user-id user-id
                            :message message}
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::ticket-message-inserted]
                 ;:on-failure [::http/http-error]
                   }})))
