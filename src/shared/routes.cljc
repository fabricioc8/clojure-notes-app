(ns shared.routes)

(def frontend-routes
  {""                                                    :board
   "candidates-board"                                    :board
   "mcdonaldslog"                                        :mcdonaldslog
   "home"                                                :home
   "candidate/new"                                       :candidate-new
   ["shared/file/" :candidate-id "/" [#".+" :file-name]] :file
   ["candidate/" :id "/view"]                            :candidate
   ["candidate/" :id "/view/" :active-tab]               :candidate-tab
   ["candidate/" :id "/edit"]                            :candidate-edit
   "login"                                               :login
   ["assignments/" :active-tab]                          :assignments-tab
   "assignments"                                         :assignments
   "teams"                                               :teams
   "tasks"                                               :tasks
   "tech-round"                                          :tech-round
   "notifications"                                       :notifications
   ["settings/" :active-tab]                             :settings
   ["settings/users/" :id "/view"]                       :settings-user
   ["settings/email-templates/" :id "/view"]             :settings-email-template
   ["settings/email-templates/" :id "/edit"]             :settings-email-template-edit
   ["settings/email-templates/new-template"]             :settings-email-template-new
   "archive"                                             :archive})
