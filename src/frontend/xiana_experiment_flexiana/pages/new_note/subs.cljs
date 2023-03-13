(ns xiana-experiment-flexiana.pages.new-note.subs
  (:require
   [xiana-experiment-flexiana.subs.app-db :as subs-db]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::note-title-input-edit
 :<- [::subs-db/view]
 (fn [view _]
   (get-in view [:new-note :note-title-input-edit])))

(rf/reg-sub
 ::note-content-text-area
 :<- [::subs-db/view]
 (fn [view _]
   (get-in view [:new-note :note-content-text-area])))

(rf/reg-sub
 ::is-public?
 :<- [::subs-db/view]
 (fn [view _]
   (get-in view [:new-note :is-public?])))

(rf/reg-sub
 ::note-id
 :<- [::subs-db/view]
 (fn [view _]
   (get-in view [:new-note :note-id])))
