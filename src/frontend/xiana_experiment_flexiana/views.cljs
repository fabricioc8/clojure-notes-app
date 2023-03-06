(ns xiana-experiment-flexiana.views
  (:require
   [re-frame.core :as re-frame]
   [xiana-experiment-flexiana.events :as events]
   [xiana-experiment-flexiana.subs :as subs]))

(defn main-panel []
  (re-frame/dispatch [::events/load-teams])
  (let [name (re-frame/subscribe [::subs/name])
        teams (re-frame/subscribe [::subs/teams])]
    [:div
     [:h1 "Hello from " @name]
     [:h1 "Teams" @teams]]))
