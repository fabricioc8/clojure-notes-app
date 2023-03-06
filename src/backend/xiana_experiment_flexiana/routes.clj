(ns xiana-experiment-flexiana.routes
  (:require
   [xiana-experiment-flexiana.controllers.invoices :as inv-con]
   [xiana-experiment-flexiana.controllers.messages :as mes-con]
   [xiana-experiment-flexiana.controllers.notes :as not-con]
   [xiana-experiment-flexiana.controllers.plans :as pla-con]
   [xiana-experiment-flexiana.controllers.subscriptions :as sub-con]
   [xiana-experiment-flexiana.controllers.teams :as team-con]
   [xiana-experiment-flexiana.controllers.tickets :as tick-con]
   [xiana-experiment-flexiana.controllers.users :as us-con]
   [xiana-experiment-flexiana.controllers.index :as index]
   [xiana-experiment-flexiana.controllers.re-frame :as re-frame]
   [reitit.ring :as ring]))

(def routes
  [["/" {:action #'index/handle-index}]
   ["/re-frame" {:action #'re-frame/handle-index}]
   ["/assets/*" (ring/create-resource-handler {:path "/"})]
   ["/api" {}
    ["/invoices" {:get {:action #'inv-con/select-all-invoices}
                  :post {:action #'inv-con/insert-invoice}}]
    ["/team-invoices/:team-id" {:get {:action #'inv-con/select-team-invoices}}]

    ["/ticket-message" {:post {:action #'mes-con/insert-ticket-message}}]
    ["/ticket-messages/:ticket-id" {:get {:action #'mes-con/select-ticket-messages}}]

    ["/notes" {:post {:action #'not-con/insert-note}
               :get {:action #'not-con/select-all-notes}}]
    ["/notes/:note-id" {:get {:action #'not-con/select-note}
                        :put {:action #'not-con/update-note}
                        :delete {:action #'not-con/delete-note}}]

    ["/plans" {:post {:action #'pla-con/insert-plan}
               :get {:action #'pla-con/select-all-plans}}]
    ["/plans/teams/:team-id" {:get {:action #'pla-con/select-team-plans}}]
    ["/plans/:plan-id" {:put {:action #'pla-con/update-plan}}]

    ["/team-subscription/:team-id" {:get {:action #'sub-con/select-current-team-subscription}}]
    ["/subscriptions" {:get {:action #'sub-con/select-all-subscriptions}}]
    ["/subscriptions/:subscription-id" {:put {:action #'sub-con/update-subscription}}]

    ["/teams/:team-id" {:get {:action #'team-con/select-team}
                        :put {:action #'team-con/update-team}}]
    ["/teams" {:get {:action #'team-con/select-all-teams}}]

    ["/tickets" {:post {:action #'tick-con/insert-ticket}}]
    ["/team-tickets/:team-id" {:get {:action #'tick-con/select-team-tickets}}]
    ["/tickets/:ticket-id" {:put {:action #'tick-con/update-ticket}}]
    ["/register-user" {:post {:action #'us-con/insert-user-by-themselve}}]

    ["/invite-user" {:post {:action #'us-con/insert-user-by-invitation}}]
    ["/users" {:get {:action #'us-con/select-all-users}}]
    ["/users/:user-id" {:get {:action #'us-con/select-user}
                        :put {:action #'us-con/update-user}}]
    ["/team-users/:team-id" {:get {:action #'us-con/select-team-users}}]]])
