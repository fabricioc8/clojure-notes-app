(ns xiana-experiment-flexiana.events.login
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]))

(rf/reg-event-db
 ::sign-up-ok
 (fn [db [_ id]]
   (prn "Sign up response")
   #_(update-in db [:entity :notes] #(util/remove-record-by-id % id))))

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
