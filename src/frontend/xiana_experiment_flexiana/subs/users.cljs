(ns xiana-experiment-flexiana.subs.users
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 ::session-user-id
 (fn [db _]
   (get-in db [:session :user-id])))

(rf/reg-sub
 ::user-team
 (fn [db _]
   (get-in db [:entity :team :id])))
