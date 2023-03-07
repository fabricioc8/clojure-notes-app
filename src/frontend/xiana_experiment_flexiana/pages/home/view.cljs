(ns xiana-experiment-flexiana.pages.home.view
  (:require
   ;["@heroicons/react/solid" :as solid-icons]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.pages.home.subs :as subs]
   [xiana-experiment-flexiana.routing.core :as routing :refer [url-for]]))

;; (def side-menu-entries
;;   [{:title "Board"
;;     :name :board
;;     :pages #{board/page
;;              candidate/page
;;              alter-candidate/page}
;;     :icon icons/board
;;     :url (url-for :board)}
;;    {:title "New candidate"
;;     :icon icons/candidate
;;     :name :candidate-new
;;     :pages #{new-candidate/page}}
;;    {:title "McDonald's Log"
;;     :name :mcdonaldslog
;;     :icon icons/mc-donalds-left-icon
;;     :url  (url-for :mcdonaldslog)
;;     :pages #{mcdonaldslog/page}}
;;    {:title "Tasks"
;;     :name :tasks
;;     :pages #{tasks/page}
;;     :icon icons/tasks
;;     :url  (url-for :tasks)}
;;    {:title "Assignments"
;;     :icon icons/assignment
;;     :name :assignments
;;     :pages #{assignments/page}
;;     :url (url-for :assignments)}
;;    {:title "Teams"
;;     :icon icons/user-group
;;     :name :teams
;;     :pages #{teams/page}
;;     :url (url-for :teams)}
;;    {:title "Tech round"
;;     :icon icons/tech-round
;;     :name :tech-round
;;     :pages #{tech-round/page}
;;     :url (url-for :tech-round)}
;;    {:title "Settings"
;;     :name :settings
;;     :pages #{settings/page}
;;     :icon icons/settings
;;     :url  (url-for :settings :active-tab :users)}])

;; (defn static-sidebar []
;;   [:div {:class "md:flex md:flex-shrink-0"}
;;    [:div {:class "flex flex-col w-28"}
;;     [side-menu/panel side-menu-entries]]])

(defn page []
  (r/with-let [current-page (rf/subscribe [::subs/current-page])]
    [:div {:class    "font-frankie h-screen flex overflow-hidden bg-white"
           ;:on-click #(rf/dispatch [::search-events/show-results (= "global-search-input" (-> % .-target .-id))])
           }
     ;[static-sidebar]
     [:div {:class "flex flex-col w-0 flex-1 overflow-hidden"}
      ;[stages/modal]
      ;[my-settings/modal]
      ;[assign-questionnaire/modal]
      ;[assign-test-task/modal]
      ;[offline/offline-modal]
      ;[candidate-modal/modal]
      ;[user-menu]
      [:main {:id "main" :tab-index -1 :class "flex-1 relative overflow-y-auto focus:outline-none"}
       [:p "Home page"]
       ;[breadcrumb]
       ;[delete-confirmation-modal]
       ;[confirmation-modal]
       (:page @current-page)
       ;[sp/panel!]
       ]]]))

(defmethod routing/resolve-view :home [_] [page])
