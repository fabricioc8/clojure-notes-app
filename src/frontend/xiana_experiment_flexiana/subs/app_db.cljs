(ns xiana-experiment-flexiana.subs.app-db
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 ::view
 (fn [db _]
   (:view db)))

(rf/reg-sub
 ::entity
 (fn [db _]
   (:entity db)))

(rf/reg-sub
 ::session
 (fn [db _]
   (:session db)))
