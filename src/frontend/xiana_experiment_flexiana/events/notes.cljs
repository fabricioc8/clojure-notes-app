(ns xiana-experiment-flexiana.events.notes
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]))

(rf/reg-event-db
 ::team-notes-selected
 (fn [db [_ response]]
   (prn "RRR" response)
   #_(let []
       (assoc-in db []))))

(rf/reg-event-fx
 ::select-team-notes
 (fn [_ [_ team-id]]
   (prn "FX")
   {:http-xhrio {:uri (util/url "/api/team-notes/" team-id)
                 :method :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [::team-notes-selected]
                 ;:on-failure [::http/http-error]
                 }}))
