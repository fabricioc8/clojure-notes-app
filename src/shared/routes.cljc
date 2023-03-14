(ns routes)

(def frontend-routes
  {;""                                                      :board1
   ;"candidates-board"                                      :board
   "auth/login"                                            :login
   "auth/register"                                         :login
   "auth/reset-password"                                   :login
   ""                                                      :dashboard
   "app"                                                   :dashboard
   ["app/note/" :note-id]                                  :new-note
   "app/api-management"                                    :api-management
   "app/settings"                                          :settings
   "app/team-settings"                                     :team-settings
   "app/billing"                                           :billing
   "app/help"                                              :tickets
   ["app/help/" :ticket-id]                                :ticket-chat


   "admin"                                                 :admin-dashboard
   "admin/support"                                         :admin-tickets
   "admin/users"                                           :admin-users
   "admin/teams"                                           :admin-teams
   "admin/plans"                                           :admin-plans
   "admin/billing"                                         :admin-billing
   "admin/cms"                                             :admin-cms})
