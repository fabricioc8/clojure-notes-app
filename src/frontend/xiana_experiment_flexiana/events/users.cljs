(ns xiana-experiment-flexiana.events.users
  (:require
   [ajax.core :as ajax]
   [xiana-experiment-flexiana.util.seq :as util]
   [re-frame.core :as rf]))

(rf/reg-event-db
 ::users-loaded
 (fn [db [_ response]]
   (let [db-users (or (get-in db [:entity :users]) {})
         loaded-users (util/associate-by :id (get-in response [:data :users]))]
     (assoc-in db [:entity :users]
               (merge db-users loaded-users)))))

(rf/reg-event-fx
 ::load-users
 (fn [_]
   {};para que llama a users aca? tiene algo que ver con los roles? o es solamente porque se necesitan para app como nombres o notificaciones asociadas?
   #_{:http-yhrio {:path "/api/users"
                 :method :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [::users-loaded]
                 ;:on-failure [::http/http-error] USAR EL DE events.http
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
 ::user-updated
 (fn [db [_ response]]
   (prn "RESPONSE" response)
   #_(let [team-users (-> response :data :users)]
     (assoc-in db [:entity :team-users] team-users))))

(rf/reg-event-fx
 ::update-user
 (fn [{:keys [db]} [_ params]]
   (let [user-id (-> db :session :user-data :id)]
     {:http-xhrio {:uri (util/url "/api/users/" user-id)
                   :method :put
                   :params params
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::user-updated]
                 ;:on-failure [::http/http-error]
                   }})))
