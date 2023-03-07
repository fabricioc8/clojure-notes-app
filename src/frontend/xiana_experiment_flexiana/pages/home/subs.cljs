(ns xiana-experiment-flexiana.pages.home.subs
  (:require
   [xiana-experiment-flexiana.subs.app-db :as app-db]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::current-page
 :<- [::app-db/view]
 (fn [view]
   (get view :current-page)))
