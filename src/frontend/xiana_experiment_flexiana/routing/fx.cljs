(ns xiana-experiment-flexiana.routing.fx
  (:require
   [xiana-experiment-flexiana.routing.dispatcher :refer [router]]
   [pushy.core :as pushy]
   [re-frame.core :as rf]
   [reagent.core :as r]))

(def prev-page-flag
  (r/atom false))

(rf/reg-fx
 :navigate-to
 (fn [path]
   (reset! prev-page-flag false)
   (pushy/set-token! router path)))

(rf/reg-fx
 :navigate-prev!
 (fn []
   (if @prev-page-flag
     (js/history.forward)
     (js/history.back))
   (swap! prev-page-flag not)))

(rf/reg-event-fx
 :redirect-prev
 (fn [{:keys [db]} _]
   (when (get-in db [:current-page :name])
     {:fx [[:navigate-prev!]]})))
