(ns xiana-experiment-flexiana.events.billing-details
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]))

(rf/reg-event-db
 ::team-billing-details-selected
 (fn [db [_ response]]
   (let [billing-details (-> response :data :billing-details first)]
     (assoc-in db [:entity :billing-details] billing-details))))

(rf/reg-event-fx
 ::select-team-billing-details
 (fn [{:keys [db]} _]
   (let [team-id (-> db :session :team-data :team-id)]
     {:http-xhrio {:uri (util/url "/api/team-billing/" team-id)
                   :method :get
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::team-billing-details-selected]
                 ;:on-failure [::http/http-error]
                   }})))

(rf/reg-event-db
 ::team-billing-details-inserted
 (fn [db [_ response]]
   (let [billing-detail (-> response :data :billing-details first)]
     (assoc-in db [:entity :billing-details] billing-detail))))

(rf/reg-event-fx
 ::insert-team-billing-details
 (fn [{:keys [db]} [_ params]]
   (let [params (into {} (filter second params))
         team-id (-> db :session :team-data :team-id)]
     {:http-xhrio {:uri "/api/billing"
                   :method :post
                   :params (assoc params :team-id team-id)
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::team-billing-details-inserted]
                 ;:on-failure [::http/http-error]
                   }})))

(rf/reg-event-db
 ::team-billing-details-update
 (fn [db [_ response]]
   (let [billing-detail (-> response :data :billing-details first)]
     (update-in db [:entity :billing-details] merge billing-detail))))

(rf/reg-event-fx
 ::update-team-billing-details
 (fn [{:keys [db]} [_ params]]
   (let [params (into {} (filter second params))
         team-id (-> db :session :team-data :team-id)]
     {:http-xhrio {:uri (util/url "/api/team-billing/" team-id)
                   :method :put
                   :params params
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::team-billing-details-update]
                 ;:on-failure [::http/http-error]
                   }})))
