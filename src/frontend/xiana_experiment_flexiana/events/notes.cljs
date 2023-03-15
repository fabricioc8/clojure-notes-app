(ns xiana-experiment-flexiana.events.notes
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.routing.core :as routing :refer [url-for]]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]))

(rf/reg-event-db
 ::all-notes-selected
 (fn [db [_ response]]
   (let [notes (-> response :data :notes)]
     (assoc-in db [:entity :all-notes] notes))))

(rf/reg-event-fx
 ::select-all-notes
 (fn [_ _]
   {:http-xhrio {:uri "/api/notes"
                 :method :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::all-notes-selected]
                 ;:on-failure [::http/http-error]
                 }}))

(rf/reg-event-db
 ::team-notes-selected
 (fn [db [_ response]]
   (let [notes (-> response :data :notes)]
     (assoc-in db [:entity :team-notes] notes))))

(rf/reg-event-fx
 ::select-team-notes
 (fn [_ [_ team-id]]
   {:http-xhrio {:uri (util/url "/api/team-notes/" team-id)
                 :method :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::team-notes-selected]
                 ;:on-failure [::http/http-error]
                 }}))

(rf/reg-event-db
 ::note-deleted
 (fn [db [_ id]]
   (update-in db [:entity :team-notes] #(util/remove-record-by-id % id))))

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

(rf/reg-event-fx
 ::new-note-inserted
 (fn [{:keys [db]} [_ response]]
   (let [note (-> response :data :notes first)]
     {:db (-> db
              (update-in [:entity :team-notes] conj note)
              (assoc-in [:view :new-note] {:note-title-input-edit (:name note)
                                           :note-id (:id note)
                                           :is-public? false
                                           :note-content-text-area ""}))
      :dispatch [:navigate (url-for :new-note
                                    :note-id (:id note))]})))

(rf/reg-event-fx
 ::insert-new-note
 (fn [_ [_ name team-id]]
   {:http-xhrio {:uri "/api/notes"
                 :method :post
                 :params {:name name
                          :team-id team-id}
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::new-note-inserted]
                 ;:on-failure [::http/http-error]
                 }}))

(rf/reg-event-fx
 ::note-updated
 (fn [{:keys [db]} [_ response]]
   (let [notes (-> response :data :notes)]
     {:db (-> db
              (update-in [:entity :team-notes] #(util/replace-by :id % notes))
              (update :view dissoc :new-note))
      :dispatch [:navigate (url-for :dashboard)]})))

(rf/reg-event-fx
 ::update-note
 (fn [_ [_ note-id params]];;is-public, name, content
   {:http-xhrio {:uri (util/url "/api/notes/" note-id)
                 :method :put
                 :params params
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::note-updated]
                 ;:on-failure [::http/http-error]
                 }}))
