(ns xiana-experiment-flexiana.events
  (:require
   [re-frame.core :as rf]
   [ajax.core :as ajax]
   [xiana-experiment-flexiana.util.seq :as util]
   [day8.re-frame.http-fx]))



(rf/reg-event-db
 ::failure
 (fn [_ _]
   (prn "failure")))

(rf/reg-event-db
 ::load-teams-success
 (fn [db [_ response]]
   (let [teams (-> response :data :teams first)]
     (assoc db :teams teams))))
;;ver porque no carga todos los equipos si le saco el first

(rf/reg-event-fx
 ::load-teams
 (fn [_ _]
   {:http-xhrio {:uri            (util/url "/api/teams")
                 :method          :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 ;:format (ajax/json-request-format)
                 :on-success      [::load-teams-success]
                 :on-failure      [::failure]}}))
