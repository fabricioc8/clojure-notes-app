(ns xiana-experiment-flexiana.views
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.events :as events]
   [xiana-experiment-flexiana.subs :as subs]))

(defn main-panel []
  (rf/dispatch [::events/load-teams])
  (let [name (rf/subscribe [::subs/name])
        teams (rf/subscribe [::subs/teams])]
    [:div
     [:h1 "Hello from " @name]
     [:h1 "Teams" @teams]]))
