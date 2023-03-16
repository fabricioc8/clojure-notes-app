(ns xiana-experiment-flexiana.events.init
  (:require
   [xiana-experiment-flexiana.events.notes :as events-notes]
   [xiana-experiment-flexiana.events.subscriptions :as events-subscriptions]
   [xiana-experiment-flexiana.events.plans :as events-plans]
   [xiana-experiment-flexiana.routing.core :refer [url-for]]
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
 ::session-ok
 (fn [{:keys [db]} [_ {:keys [data]}]]
   (let [admin? (= (-> data :user-data :user-role) "admin")]
     {:db (update db :session merge data)
      :fx [(if admin?
             [:navigate-to (url-for :admin-dashboard)]
             [:navigate-to (url-for :dashboard)])
           [:dispatch-n (list [::events-notes/select-team-notes (-> data :team-data :team-id)]
                              [::events-subscriptions/select-current-subscription]
                              [::events-plans/select-team-plans])]]})))
(rf/reg-event-fx
 ::force-logout
 (fn [{:keys [db]} _]
   {:db (dissoc db :session :view :entity)
    :fx [(rf/dispatch [:navigate "/login"])]}))

(rf/reg-event-fx
 ::session-open
 (fn [_ _]
   {:http-xhrio {:uri "/api/session-open"
                 :method :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::session-ok]
                 :on-failure [::force-logout]}}))
