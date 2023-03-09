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
   (let [team-id (-> db :session :team :id)]
     {:http-xhrio {:uri (util/url "/api/team-subscription/" team-id)
                   :method :get
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::current-subscription-selected]
                 ;:on-failure [::http/http-error]
                   }})))
