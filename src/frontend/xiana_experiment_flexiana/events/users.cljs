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
