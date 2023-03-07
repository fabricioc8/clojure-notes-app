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
