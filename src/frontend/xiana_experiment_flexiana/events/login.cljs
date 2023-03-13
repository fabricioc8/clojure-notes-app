(ns xiana-experiment-flexiana.events.login
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.events.notes :as events-notes]
   [xiana-experiment-flexiana.events.subscriptions :as events-subscriptions]
   [xiana-experiment-flexiana.events.plans :as events-plans]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]
   [xiana-experiment-flexiana.routing.core :refer [url-for]]))

(rf/reg-event-fx
 ::session-ok
 (fn [{:keys [db]} [_ {:keys [data]}]]
   {:db (update db :session merge data)
    :fx [[:navigate-to (url-for :dashboard)]
         [:dispatch-n (list [::events-notes/select-team-notes (-> data :team-data :team-id)]
                            [::events-subscriptions/select-current-subscription]
                            [::events-plans/select-team-plans])]]}))

(rf/reg-event-fx
 ::sign-up
 (fn [_ [_ email password team-name]]
   {:http-xhrio {:uri (util/url "/api/register-user")
                 :method :post
                 :params {:email email
                          :password password
                          :name team-name}
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::session-ok]
                 ;:on-failure [::http/http-error]
                 }}))

(rf/reg-event-db
 ::logout
 (fn [db _]
   (update db :session dissoc :user-data)))

(rf/reg-event-fx
 ::login
 (fn [_ [_ email password]]
   {:http-xhrio {:uri "api/login"
                 :method :post
                 :params {:email email
                          :password password}
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::session-ok]
                 ;:on-failure [::http/http-error]
                 }}))
