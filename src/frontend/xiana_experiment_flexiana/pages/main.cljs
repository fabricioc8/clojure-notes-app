(ns xiana-experiment-flexiana.pages.main
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.events :as events]
   [xiana-experiment-flexiana.subs :as subs]
   [xiana-experiment-flexiana.pages.home.view :as home]
   [xiana-experiment-flexiana.pages.dashboard.view]))

(defn main-panel []
  (rf/dispatch [::events/load-teams])
  #_(let [name (rf/subscribe [::subs/name])
          teams (rf/subscribe [::subs/teams])]
      [:div
       [:h1 "Hello from " @name]
       [:h1 "Teams" @teams]])
  [home/page])
