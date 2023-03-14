(ns xiana-experiment-flexiana.routing.routes
  (:require
   [bidi.bidi :as bidi]
   [xiana-experiment-flexiana.events.users :as users]
   [re-frame.core :as rf]
   [routes  :refer [frontend-routes]]))

(def routes
  ["/" frontend-routes])

(def ^:export url-for (partial bidi/path-for routes))

(def ^:export global-navigation-events
  [])

(defn ^:export dispatch-global-evts []
  (doseq [evt global-navigation-events]
    (rf/dispatch evt)))
