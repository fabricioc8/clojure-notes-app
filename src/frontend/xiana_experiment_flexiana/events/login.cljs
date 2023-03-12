(ns xiana-experiment-flexiana.events.login
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]
   [xiana-experiment-flexiana.routing.core :refer [url-for]]))

(rf/reg-event-fx
 ::sign-up-ok
 (fn [{:keys [db]} [_ {:keys [data]}]]
   {:db (update db :session merge data)
    :fx [[:navigate-to (url-for :dashboard)]]}))

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
                 :on-success [::sign-up-ok]
                 ;:on-failure [::http/http-error]
                 }}))

(rf/reg-event-db
 ::logout
 (fn [db _]
   (update db :session dissoc :user-data)))

