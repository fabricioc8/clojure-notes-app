(ns xiana-experiment-flexiana.pages.new-note.events
  (:require
   [re-frame.core :as rf]))

(rf/reg-event-db
 ::note-title-input-edit
 (fn [db [_ value]]
   (assoc-in db [:view :new-note :note-title-input-edit] value)))

(rf/reg-event-db
 ::note-content-text-area
 (fn [db [_ value]]
   (assoc-in db [:view :new-note :note-content-text-area] value)))

(rf/reg-event-db
 ::is-public?
 (fn [db [_]]
   (update-in db [:view :new-note :is-public?] not)))
