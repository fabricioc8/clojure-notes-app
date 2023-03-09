(ns xiana-experiment-flexiana.pages.team-settings.events
  (:require
   [re-frame.core :as rf]))

(rf/reg-event-db
 ::team-name-input
 (fn [db [_ value]]
   (assoc-in db [:view :team-settings :team-name-input] value)))

(rf/reg-event-db
 ::reset-team-name-input
 (fn [db _]
   (update db :view dissoc :team-settings)))

(rf/reg-event-db
 ::invite-email-input
 (fn [db [_ value]]
   (assoc-in db [:view :team-settings :invite-email-input] value)))

(rf/reg-event-db
 ::team-role-selector
 (fn [db [_ value]]
   (assoc-in db [:view :team-settings :team-role-selector] value)))
