(ns xiana-experiment-flexiana.subs.ticket-messages
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.subs.app-db :as subs-db]))

(rf/reg-sub
 ::ticket-messages
 :<- [::subs-db/entity]
 (fn [entity _]
   (get entity :current-ticket-messages)))
