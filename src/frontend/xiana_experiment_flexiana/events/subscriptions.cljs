(ns xiana-experiment-flexiana.events.subscriptions
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]))

(rf/reg-event-db
 ::current-subscription-selected
 (fn [db [_ response]]
   (let [subscription (-> response :data :subscriptions first)]
     (assoc-in db [:entity :current-subscription] subscription))))

(rf/reg-event-fx
 ::select-current-subscription
 (fn [{:keys [db]} _]
   (let [team-id (-> db :session :team-data :team-id)]
     {:http-xhrio {:uri (util/url "/api/team-subscription/" team-id)
                   :method :get
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::current-subscription-selected]
                 ;:on-failure [::http/http-error]
                   }})))

(rf/reg-event-db
 ::subscription-inserted
 (fn [db [_ response]]
   (let [subscription (-> response :data :subscriptions first)]
     (assoc-in db [:entity :current-subscription] subscription))))

(rf/reg-event-fx
 ::insert-subscription
 (fn [{:keys [db]} [_ plan-id]]
   (let [team-id (-> db :session :team-data :team-id)]
     {:http-xhrio {:uri "/api/subscriptions"
                   :method :post
                   :params {:team-id team-id
                            :plan-id plan-id}
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::subscription-inserted]
                 ;:on-failure [::http/http-error]
                   }})))

(rf/reg-event-db
 ::all-subscriptions-selected
 (fn [db [_ response]]
   (let [subscriptions (-> response :data :subscriptions)]
     (assoc-in db [:entity :subscriptions] subscriptions))))

(rf/reg-event-fx
 ::select-all-subscriptions
 (fn [_ _]
   {:http-xhrio {:uri "/api/subscriptions"
                 :method :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::all-subscriptions-selected]
                 ;:on-failure [::http/http-error]
                 }}))
(rf/dispatch [::select-all-subscriptions])
