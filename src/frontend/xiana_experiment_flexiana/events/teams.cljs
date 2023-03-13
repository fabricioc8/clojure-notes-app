(ns xiana-experiment-flexiana.events.teams
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]))

(rf/reg-event-db
 ::team-updated
 (fn [db [_ response]]
   (let [team (-> response :data :teams first)]
     (assoc-in db [:session :team] team))))

(rf/reg-event-fx
 ::update-team
 (fn [{:keys [db]} [_ params]]
   (let [team-id (-> db :session :team-data :team-id)]
     {:http-xhrio {:uri (util/url "/api/teams/" team-id)
                   :method :put
                   :params params
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::team-updated]
                 ;:on-failure [::http/http-error]
                   }})))

(rf/reg-event-db
 ::user-team-selected
 (fn [db [_ response]]
   (let [team (-> response :data :users first)]
     (assoc-in db [:entity :user-team] team))))

(rf/reg-event-fx
 ::select-user-team
 (fn [{:keys [db]} [_]]
   (let [user-id (-> db :session :user-data :id)]
     {:http-xhrio {:uri (util/url "/api/user-team/" user-id)
                   :method :get
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::user-team-selected]
                 ;:on-failure [::http/http-error]
                   }})))
