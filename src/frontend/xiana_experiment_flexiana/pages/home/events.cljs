(ns xiana-experiment-flexiana.pages.home.events
  (:require
   [ajax.core :as ajax]
   [day8.re-frame.http-fx]
   [re-frame.core :as rf]
   [taoensso.timbre :as log]))

(rf/reg-event-db
 ::clean-current-state
 (fn [db _]
   (update-in db [:view :candidate-new] dissoc :state-id)))

(rf/reg-event-db
 ::current-page
 (fn [db [_ page]]
   (assoc-in db [:view :current-page] page)))
