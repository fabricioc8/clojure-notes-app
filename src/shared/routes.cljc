(ns routes)

(def frontend-routes
  {;""                                                      :board1
   ;"candidates-board"                                      :board
   "" :dashboard
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
   "admin/cms"                                             :admin-cms



   "mcdonaldslog"                                          :mcdonaldslog
   "home"                                                  :home
   "candidate/new"                                         :candidate-new
   ["shared/file/" :candidate-id "/" [#".+" :file-name]] :file
   ["candidate/" :id "/view"]                            :candidate
   ["candidate/" :id "/view/" :active-tab]               :candidate-tab
   ["candidate/" :id "/edit"]                            :candidate-edit
   "login"                                                 :login
   ["assignments/" :active-tab]                          :assignments-tab
   "assignments"                                           :assignments
   "teams"                                                 :teams
   "tasks"                                                 :tasks
   "tech-round"                                            :tech-round
   "notifications"                                         :notifications
   ["settings/" :active-tab]                             :settings
   ["settings/users/" :id "/view"]                       :settings-user
   ["settings/email-templates/" :id "/view"]             :settings-email-template
   ["settings/email-templates/" :id "/edit"]             :settings-email-template-edit
   ["settings/email-templates/new-template"]             :settings-email-template-new
   "archive"                                               :archive})
