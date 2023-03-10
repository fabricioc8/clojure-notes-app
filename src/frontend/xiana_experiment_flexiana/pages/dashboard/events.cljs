(ns xiana-experiment-flexiana.pages.dashboard.events
  (:require
   [re-frame.core :as rf]))

(rf/reg-event-db
 ::note-title-input
 (fn [db [_ input]]
   (assoc-in db [:view :dashboard :note-title-input] input)))

(rf/reg-event-db
 ::reset-note-title-input
 (fn [db _]
   (update-in db [:view :dashboard] dissoc :note-title-input)))
