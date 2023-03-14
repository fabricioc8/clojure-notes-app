(ns xiana-experiment-flexiana.events.tickets
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]))

(rf/reg-event-db
 ::all-tickets-selected
 (fn [db [_ response]]
   (let [tickets (-> response :data :tickets)]
     (assoc-in db [:entity :all-tickets] tickets))))

(rf/reg-event-fx
 ::select-all-tickets
 (fn [_ _]
   {:http-xhrio {:uri "/api/tickets"
                 :method :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::all-tickets-selected]
                 ;:on-failure [::http/http-error]
                 }}))

(rf/reg-event-db
 ::team-tickets-selected
 (fn [db [_ response]]
   (let [tickets (-> response :data :tickets)]
     (assoc-in db [:entity :all-tickets] tickets))))

(rf/reg-event-fx
 ::select-team-tickets
 (fn [{:keys [db]} _]
   (let [team-id (-> db :session :team-data :team-id)]
     {:http-xhrio {:uri (util/url "/api/team-tickets/" team-id)
                   :method :get
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::team-tickets-selected]
                 ;:on-failure [::http/http-error]
                   }})))

(rf/reg-event-db
 ::ticket-inserted
 (fn [db [_ response]]
   (let [ticket (-> response :data :tickets first)]
     (update-in db [:entity :all-tickets] conj ticket))))

(rf/reg-event-fx
 ::insert-ticket
 (fn [{:keys [db]} [_ name]]
   (let [team-id (-> db :session :team-data :team-id)]
     {:http-xhrio {:uri "/api/tickets"
                   :method :post
                   :params {:name name
                            :team-id team-id}
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::ticket-inserted]
                 ;:on-failure [::http/http-error]
                   }})))

(rf/reg-event-db
 ::ticket-updated
 (fn [db [_ response]]
   (let [ticket (-> response :data :tickets)]
     (update-in db [:entity :all-tickets] #(util/replace-by :id % ticket)))))

(rf/reg-event-fx
 ::update-ticket
 (fn [_ [_ ticket-id resolved]]
   {:http-xhrio {:uri (util/url "/api/tickets/" ticket-id)
                 :method :put
                 :params {:resolved resolved}
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::ticket-updated]
                 ;:on-failure [::http/http-error]
                 }}))
