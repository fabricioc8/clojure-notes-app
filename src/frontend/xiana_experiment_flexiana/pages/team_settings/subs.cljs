(ns xiana-experiment-flexiana.pages.team-settings.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 ::team-name-input
 (fn [db _]
   (get-in db [:view :team-settings :team-name-input])))

(rf/reg-sub
 ::invite-email-input
 (fn [db _]
   (get-in db [:view :team-settings :invite-email-input])))

(rf/reg-sub
 ::team-role-selector
 (fn [db _]
   (get-in db [:view :team-settings :team-role-selector])))
