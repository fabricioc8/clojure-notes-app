(ns xiana-experiment-flexiana.routing.events
  (:require
   [xiana-experiment-flexiana.routing.fx]
   [xiana-experiment-flexiana.routing.routes :as r]
   [re-frame.core :as rf]))

(rf/reg-event-fx
 :navigate
 (fn [_ [_ path]]
   {:navigate-to path}))

(rf/reg-event-fx
 :navigate-to-page
 (fn [_ [_ page & params]]
   {:navigate-to (apply r/url-for page params)}))

(rf/reg-event-fx
 :page/previous-page
 (fn []
   {:navigate-prev! nil}))
