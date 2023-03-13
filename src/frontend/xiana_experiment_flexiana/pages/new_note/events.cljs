(ns xiana-experiment-flexiana.pages.new-note.events
  (:require
   [re-frame.core :as rf]))

(rf/reg-event-db
 ::note-title-input-edit
 (fn [db [_ value]]
   (assoc-in db [:view :new-note :note-title-input-edit] value)))
