(ns xiana-experiment-flexiana.events.teams
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]))

(rf/reg-event-db
 ::team-updated
 (fn [db [_ response]]
   (let [team (-> response :data :teams first)]
     (-> db
         (update-in [:session :team-data]
                    (fn [team-data] (if (= (:team-id team-data)
                                           (:id team))
                                      (merge team-data team)
                                      team-data)))
         (update-in [:entity :teams] #(util/replace-by :id % [team]))))))

(rf/reg-event-fx
 ::update-team
 (fn [{:keys [db]} [_ params]]
   (let [team-id (-> db :session :team-data :team-id)]
     {:http-xhrio {:uri (util/url "/api/teams/" (or (:id params) team-id))
                   :method :put
                   :params params
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::team-updated]
                 ;:on-failure [::http/http-error]
                   }})))

(rf/reg-event-db
 ::all-teams-selected
 (fn [db [_ response]]
   (let [teams (-> response :data :teams)]
     (assoc-in db [:entity :teams] teams))))

(rf/reg-event-fx
 ::select-all-teams
 (fn [_ _]
   {:http-xhrio {:uri             "/api/teams"
                 :method          :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success      [::all-teams-selected]
                 ;:on-failure      [::failure]
                 }}))
