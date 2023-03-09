(ns xiana-experiment-flexiana.events.tickets
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]))

(rf/reg-event-db
 ::team-tickets-selected
 (fn [db [_ response]]
   (let [tickets (-> response :data :tickets)]
     (assoc-in db [:entity :tickets] tickets))))

(rf/reg-event-fx
 ::select-team-tickets
 (fn [{:keys [db]} _]
   (let [team-id (-> db :session :team :id)]
     {:http-xhrio {:uri (util/url "/api/team-tickets/" team-id)
                   :method :get
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::team-tickets-selected]
                 ;:on-failure [::http/http-error]
                   }})))
;(rf/dispatch [::select-team-tickets])
