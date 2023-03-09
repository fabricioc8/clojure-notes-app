(ns xiana-experiment-flexiana.events.notes
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]))

(rf/reg-event-db
 ::team-notes-selected
 (fn [db [_ response]]
   (let [notes (-> response :data :notes)]
     (assoc-in db [:entity :notes] notes))))

(rf/reg-event-fx
 ::select-team-notes
 (fn [{:keys [db]} _]
   (let [team-id (-> db :session :team :user-team-id)]
     {:http-xhrio {:uri (util/url "/api/team-notes/" team-id)
                   :method :get
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::team-notes-selected]
                 ;:on-failure [::http/http-error]
                   }})))

(rf/reg-event-db
 ::note-deleted
 (fn [db [_ id]]
   (update-in db [:entity :notes] #(util/remove-record-by-id % id))))

(rf/reg-event-fx
 ::delete-note
 (fn [_ [_ note-id]]
   {:http-xhrio {:uri (util/url "/api/notes/" note-id)
                 :method :delete
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::note-deleted note-id]
                 ;:on-failure [::http/http-error]
                 }}))
;(rf/dispatch [::delete-note "7dc83523-835e-4663-b7dc-12a4d65a7d79"])
