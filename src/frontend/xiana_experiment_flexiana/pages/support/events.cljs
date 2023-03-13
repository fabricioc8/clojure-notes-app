(ns xiana-experiment-flexiana.pages.support.events
  (:require
   [re-frame.core :as rf]))

(rf/reg-event-db
 ::ticket-name-input
 (fn [db [_ value]]
   (assoc-in db [:view :support :ticket-name-input] value)))
