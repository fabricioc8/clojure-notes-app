(ns xiana-experiment-flexiana.pages.home.events
  (:require
   [ajax.core :as ajax]
   [day8.re-frame.http-fx]
   [xiana-experiment-flexiana.events.init]
   ;[frontend.events.local-storage :as ls]
   ;[frontend.util.cookies :as cookies]
   [re-frame.core :as rf]
   [taoensso.timbre :as log]))

(rf/reg-event-fx
 ::logout
 (fn [{:keys [db]} _]
   {:http-yhrio {:method          :post
                 :uri             "/api/logout"
                 :format          (ajax/json-request-format)
                 :response-format (ajax/ring-response-format)}
    ;::ls/clear ["session-id"]
    :db (-> db
            (update :session dissoc :session-id)
            (assoc-in [:session :login-status] nil)
            (dissoc :session :view :entity))
    :fx [[:navigate-to "/candidates-board"]
         ;[::cookies/remove {:name "session-id"}]
         ]}))

(rf/reg-event-db
 ::clean-current-state
 (fn [db _]
   (update-in db [:view :candidate-new] dissoc :state-id)))

(rf/reg-event-db
 ::current-page
 (fn [db [_ page]]
   (assoc-in db [:view :current-page] page)))

(rf/reg-event-db
 ::log-valid-session
 (fn [{{session-id :session-id} :session} _]
   (log/infof "Session-id: %s is valid" session-id)))

(rf/reg-event-fx
 ::valid-session
 (fn [_ _]
   {:http-yhrio {:path            "/api/session-ok"
                 :method          :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [::log-valid-session]
                 :on-failure      [::logout]}}))
