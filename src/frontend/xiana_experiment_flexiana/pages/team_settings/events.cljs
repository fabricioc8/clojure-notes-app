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
   (update-in db [:view :team-settings] dissoc :team-name-input)))
