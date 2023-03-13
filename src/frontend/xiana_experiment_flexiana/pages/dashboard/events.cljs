(ns xiana-experiment-flexiana.pages.dashboard.events
  (:require
   [xiana-experiment-flexiana.routing.core :as routing :refer [url-for]]
   [re-frame.core :as rf]))

(rf/reg-event-db
 ::note-title-input
 (fn [db [_ input]]
   (assoc-in db [:view :dashboard :note-title-input] input)))

(rf/reg-event-db
 ::reset-note-title-input
 (fn [db _]
   (update-in db [:view :dashboard] dissoc :note-title-input)))

(rf/reg-event-fx
 ::provide-update-note
 (fn [{:keys [db]} [_ values]]
   {:db (assoc-in db [:view :new-note] {:note-title-input-edit (:name values)
                                        :note-id (:id values)
                                        :is-public? (:is-public values)
                                        :note-content-text-area (:content values)})
    :dispatch [:navigate (url-for :new-note
                                  :note-id (:id values))]}))
