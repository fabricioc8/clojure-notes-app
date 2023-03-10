(ns xiana-experiment-flexiana.pages.login.subs
  (:require
   [xiana-experiment-flexiana.subs.app-db :as app-db]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::sign-up-email-input
 :<- [::app-db/view]
 (fn [view]
   (get-in view [:login :sign-up-email-input])))

(rf/reg-sub
 ::sign-up-password-input
 :<- [::app-db/view]
 (fn [view]
   (get-in view [:login :sign-up-password-input])))
