(ns xiana-experiment-flexiana.events.plans
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]))

(rf/reg-event-db
 ::team-selected
 (fn [db [_ response]]
   (let [team-plans (-> response :data :plans)]
     (assoc-in db [:entity :team-plans] team-plans))))

(rf/reg-event-fx
 ::select-team-plans
 (fn [{:keys [db]} _]
   (let [team-id (-> db :session :team :id)]
     {:http-xhrio {:uri (util/url "/api/team-plans/" team-id)
                   :method :get
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::team-selected]
                 ;:on-failure [::http/http-error]
                   }})))
