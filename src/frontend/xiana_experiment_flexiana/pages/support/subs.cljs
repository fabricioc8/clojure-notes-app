(ns xiana-experiment-flexiana.pages.support.subs
  (:require
   [xiana-experiment-flexiana.subs.app-db :as app-db]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::ticket-name-input
 :<- [::app-db/view]
 (fn [view _]
   (get-in view [:support :ticket-name-input])))
