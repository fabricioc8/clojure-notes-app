(ns xiana-experiment-flexiana.events.users
  (:require
   [ajax.core :as ajax]
   [xiana-experiment-flexiana.util.seq :as util]
   [re-frame.core :as rf]
   [clojure.set :as ss]))

(rf/reg-event-db
 ::all-users-selected
 (fn [db [_ response]]
   (let [users (-> response :data :users)]
     (assoc-in db [:entity :users] users))))

(rf/reg-event-fx
 ::select-all-users
 (fn [_ _]
   {:http-xhrio {:uri "/api/users" 
                 :method :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::all-users-selected]
                 ;:on-failure [::http/http-error]
                 }}))

(rf/reg-event-db
 ::user-team-selected
 (fn [db [_ response]]
   (let [team (-> response :data :users first)]
     (assoc-in db [:entity :team] team))))

(rf/reg-event-fx
 ::select-user-team
 (fn [_ [_ user-id]]
   {:http-xhrio {:uri (util/url "/api/user-team/" user-id)
                 :method :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [::user-team-selected]
                 ;:on-failure [::http/http-error]
                 }}))

(rf/reg-event-db
 ::team-users-selected
 (fn [db [_ response]]
   (let [team-users (-> response :data :users)]
     (assoc-in db [:entity :team-users] team-users))))

(rf/reg-event-fx
 ::select-team-users
 (fn [{:keys [db]} _]
   (let [team-id (-> db :session :team-data :team-id)]
     {:http-xhrio {:uri (util/url "/api/team-users/" team-id)
                   :method :get
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::team-users-selected]
                 ;:on-failure [::http/http-error]
                   }})))

(rf/reg-event-db
 ::teams-users-selected
 (fn [db [_ response]]
   (let [teams-users (-> response :data :users)]
     (assoc-in db [:entity :teams-users] teams-users))))

(rf/reg-event-fx
 ::select-teams-users
 (fn [_ _]
   {:http-xhrio {:uri "/api/teams-users"
                 :method :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::teams-users-selected]
                 ;:on-failure [::http/http-error]
                 }}))

(rf/reg-event-db
 ::user-updated
 (fn [db [_ response]]
   (let [user (-> response :data :users first)
         full-user (ss/rename-keys user {:id :user-id
                                         :enabled :user-enabled})]
     (-> db
         (update-in [:entity :users] #(util/replace-by :id % [user]))
         (update-in [:entity :teams-users] #(util/merge-by :user-id % [full-user]))))))

(rf/reg-event-fx
 ::update-user
 (fn [{:keys [db]} [_ params]]
   (let [user-id (-> db :session :user-data :id)]
     {:http-xhrio {:uri (util/url "/api/users/" (or (:id params) user-id))
                   :method :put
                   :params params
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::user-updated]
                 ;:on-failure [::http/http-error]
                   }})))
