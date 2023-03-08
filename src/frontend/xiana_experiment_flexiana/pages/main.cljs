(ns xiana-experiment-flexiana.pages.main
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.events :as events]
   [xiana-experiment-flexiana.subs :as subs]
   [xiana-experiment-flexiana.pages.home.view :as home]
   [xiana-experiment-flexiana.pages.dashboard.view]
   [xiana-experiment-flexiana.pages.api-management.view]
   [xiana-experiment-flexiana.pages.billing.view]
   [xiana-experiment-flexiana.pages.settings.view]
   [xiana-experiment-flexiana.pages.support.view]
   [xiana-experiment-flexiana.pages.team-settings.view]
   [xiana-experiment-flexiana.admin-pages.admin-billing.view]
   [xiana-experiment-flexiana.admin-pages.admin-cms.view]
   [xiana-experiment-flexiana.admin-pages.admin-dashboard.view]
   [xiana-experiment-flexiana.admin-pages.admin-plans.view]
   [xiana-experiment-flexiana.admin-pages.admin-support.view]
   [xiana-experiment-flexiana.admin-pages.admin-teams.view]
   [xiana-experiment-flexiana.admin-pages.admin-users.view]))

(defn main-panel []
  (rf/dispatch [::events/load-teams])
  #_(let [name (rf/subscribe [::subs/name])
          teams (rf/subscribe [::subs/teams])]
      [:div
       [:h1 "Hello from " @name]
       [:h1 "Teams" @teams]])
  [home/page])
