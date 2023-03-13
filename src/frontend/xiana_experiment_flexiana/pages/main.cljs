(ns xiana-experiment-flexiana.pages.main
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.subs.users :as subs-users]
   [xiana-experiment-flexiana.pages.home.view :as home]
   [xiana-experiment-flexiana.pages.login.view :as login]
   [xiana-experiment-flexiana.pages.dashboard.view]
   [xiana-experiment-flexiana.pages.api-management.view]
   [xiana-experiment-flexiana.pages.billing.view]
   [xiana-experiment-flexiana.pages.settings.view]
   [xiana-experiment-flexiana.pages.support.view]
   [xiana-experiment-flexiana.pages.ticket-messages.view]
   [xiana-experiment-flexiana.pages.team-settings.view]
   [xiana-experiment-flexiana.admin-pages.admin-billing.view]
   [xiana-experiment-flexiana.admin-pages.admin-cms.view]
   [xiana-experiment-flexiana.admin-pages.admin-dashboard.view]
   [xiana-experiment-flexiana.admin-pages.admin-plans.view]
   [xiana-experiment-flexiana.admin-pages.admin-support.view]
   [xiana-experiment-flexiana.admin-pages.admin-teams.view]
   [xiana-experiment-flexiana.admin-pages.admin-users.view]))

(defn main-panel []
  (let [user-data @(rf/subscribe [::subs-users/session-user-data])]
    (if user-data
      [home/page]
      [login/page])))
