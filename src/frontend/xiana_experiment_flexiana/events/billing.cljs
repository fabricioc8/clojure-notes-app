(ns xiana-experiment-flexiana.events.billing
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]))

(rf/reg-event-db
 ::billing-details-selected
 (fn [db [_ response]]
   (let [invoices (-> response :data :invoices)]
     (assoc-in db [:entity :invoices] invoices))))

(rf/reg-event-fx
 ::select-billing-details
 (fn [{:keys [db]} _]
   (let [team-id (-> db :session :team-data :team-id)]
     {:http-xhrio {:uri (util/url "/api/team-invoices/" team-id)
                   :method :get
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::billing-details-selected]
                 ;:on-failure [::http/http-error]
                   }})))
;;FALTAN ENDPOINTS
