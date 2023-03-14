(ns xiana-experiment-flexiana.subs.notes
  (:require
   [xiana-experiment-flexiana.subs.app-db :as db]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::all-notes
 :<- [::db/entity]
 (fn [entity _]
   (get entity :all-notes)))

(rf/reg-sub
 ::team-notes
 :<- [::db/entity]
 (fn [entity [_]]
   (get entity :team-notes)))
