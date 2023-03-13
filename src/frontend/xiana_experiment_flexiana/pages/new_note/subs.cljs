(ns xiana-experiment-flexiana.pages.new-note.subs
  (:require
   [xiana-experiment-flexiana.subs.app-db :as subs-db]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::note-title-input-edit
 :<- [::subs-db/view]
 (fn [view _]
   (get-in view [:new-note :note-title-input-edit])))
