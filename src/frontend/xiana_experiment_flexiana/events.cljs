(ns xiana-experiment-flexiana.events
  (:require
   [re-frame.core :as re-frame]
   [xiana-experiment-flexiana.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))
