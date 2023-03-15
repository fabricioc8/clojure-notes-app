(ns xiana-experiment-flexiana.events.init
  (:require
   [xiana-experiment-flexiana.events.login :as events-login]
   [ajax.core :as ajax]
   [re-frame.core :as rf]))

(def initial-db
  {:login-email-field                   nil
   :login-status                        nil
   :active-remember-me?                 false
   :session                             {#_#_#_#_:user {:user-id "d9530872-2958-4784-bad7-1feaa984d53e" ;(storage-> "user-id")
                                                :session-id nil} ;(storage-> "session-id")
                                         :team {:id "3c033fb5-162c-4bb7-b8ea-8a4a26019409"
                                                :name "Team1"}}
   })

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   initial-db))

(rf/reg-event-fx
 ::force-logout
 (fn [_ _]
   {:db nil
    :fx [(rf/dispatch [:navigate "/login"])]}))

(rf/reg-event-fx
 ::session-open?
 (fn [_ _]
   {:http-xhrio {:uri "/api/session-open"
                 :method :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::events-login/session-ok]
                 :on-failure [::force-logout]}}))
