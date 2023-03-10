(ns xiana-experiment-flexiana.pages.dashboard.subs
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.subs.app-db :as app-db]))

(rf/reg-sub
 ::note-title-input
 :<- [::app-db/view]
 (fn [view _]
   (get-in view [:dashboard :note-title-input])))
