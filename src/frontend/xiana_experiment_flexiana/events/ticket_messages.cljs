(ns xiana-experiment-flexiana.events.ticket-messages
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]))

(rf/reg-event-db
 ::ticket-messages-selected
 (fn [db [_ response]]
   (let [ticket-messages (-> response :data :messages)
         db (assoc-in db [:entity :current-ticket-messages] ticket-messages)
         _ (prn (:entity db))]
       db )))

(rf/reg-event-fx
 ::select-ticket-messages
 (fn [_ [_ ticket-id]]
   {:http-xhrio {:uri (util/url "/api/ticket-messages/" ticket-id)
                 :method :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::ticket-messages-selected]
                 ;:on-failure [::http/http-error]
                 }}))
