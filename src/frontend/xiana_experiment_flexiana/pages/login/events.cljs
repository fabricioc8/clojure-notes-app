(ns xiana-experiment-flexiana.pages.login.events
  (:require
   [re-frame.core :as rf]))

(rf/reg-event-db
 ::sign-up-email-input
 (fn [db [_ email]]
   (assoc-in db [:view :login :sign-up-email-input] email)))

(rf/reg-event-db
 ::sign-up-password-input
 (fn [db [_ password]]
   (assoc-in db [:view :login :sign-up-password-input] password)))
