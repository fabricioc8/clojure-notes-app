(ns xiana-experiment-flexiana.events.login
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.events.init :as events-init]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]))

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
                 :on-success [::events-init/session-ok]
                 :on-failure [::events-init/force-logout]}}))

(rf/reg-event-fx
 ::login
 (fn [_ [_ email password]]
   {:http-xhrio {:uri "api/login"
                 :method :post
                 :params {:email email
                          :password password}
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::events-init/session-ok]
                 :on-failure [::events-init/force-logout]}}))
                 ;:on-failure [::http/http-error]
                 }}))
