(ns xiana-experiment-flexiana.pages.home.view
  (:require
   ;["@heroicons/react/solid" :as solid-icons]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.routing.core :as routing :refer [url-for]]
   [xiana-experiment-flexiana.pages.home.subs :as subs]
   [xiana-experiment-flexiana.events.login :as events-login]
   [xiana-experiment-flexiana.pages.dashboard.view :as dashboard]
   [xiana-experiment-flexiana.pages.api-management.view :as api-management]
   [xiana-experiment-flexiana.pages.settings.view :as settings]
   [xiana-experiment-flexiana.pages.team-settings.view :as team-settings]
   [xiana-experiment-flexiana.pages.billing.view :as billing]
   [xiana-experiment-flexiana.pages.support.view :as support]))

(defn panel
  "side-menu-entries is a vector of maps, each of which contains :title, :icon, and :page.
  :title is a string
  :icon is a function which accepts a single parameter and returns a hiccup svg file
  :page is a reagent component containing the page to be displayed"

  [side-menu-entries]
  [:div {:class "flex-initial space-y-5 flex flex-col z-40 flex-shrink-0 bg-dark-main pt-6
                 pb-4 overflow-y-auto w-48 fixed h-full"}
   (let [active-window (rf/subscribe [::subs/current-page])]
     [:nav {#_#_:class "flex-1 px-2 space-y-1" :data-ci :app-nav}
      (doall
       (for [{:keys [title icon url pages disabled?] :as menu-item} side-menu-entries
             :let [#_#_icon-colour   (if (some pages [(first (:page @active-window))])
                                   "text-frankie-gold bg-dark-secondary"
                                   "text-gray-400")]]
         ^{:key title}
         [:a (merge
              {:title (if-not disabled? title "Not implemented yet")
               :href  (if-not disabled? (if url url "#") "#")
               #_#_:class [(when disabled? "cursor-not-allowed")
                       "group flex flex-col h-18 justify-center items-center text-xs font-medium rounded-md"
                       icon-colour]}
              (when-not url
                {:on-click #(do
                              ;(rf/dispatch [::events/clean-current-state])
                              (rf/dispatch [:navigate (url-for (:name menu-item))]))}))
          ;[icon "#9CA3AF" #_icon-colour]
          [:span {:class "block text-center mt-2"} title]]))])])

(def side-menu-entries
  [{:title "Dashboard"
    :name :dashboard
    :pages #{dashboard/page}
    ;:icon icons/board
    :url (url-for :dashboard)}
   {:title "Settings"
    :name :settings
    ;:icon icons/mc-donalds-left-icon
    :url  (url-for :settings)
    :pages #{settings/page}}
   {:title "API management"
    ;:icon icons/candidate
    :name :api-management
    :url (url-for :api-management)
    :pages #{api-management/page}}
   {:title "Team settings"
    :name :team-settings
    :pages #{team-settings/page}
    ;:icon icons/tasks
    :url  (url-for :team-settings)}
   {:title "Billing"
    ;:icon icons/assignment
    :name :billing
    :pages #{billing/page}
    :url (url-for :billing)}
   {:title "Support"
    ;:icon icons/user-group
    :name :tickets
    :pages #{support/page}
    :url (url-for :tikets)}])

(defn static-sidebar []
  [:div {:class "w-48 text-white bg-[#36393D]"}
   [panel side-menu-entries]])

(defn page []
  (r/with-let [current-page (rf/subscribe [::subs/current-page])]
    [:div {:class "font-frankie h-full flex-col overflow-hidden bg-white"}
     [:div {:class "h-10 w-full absolute bg-[#3666A7]"}
      [:div {:class "flex justify-end"} 
       [:button {:on-click #(rf/dispatch [::events-login/logout])}
        "Menu"]]]
     [:div {:class "flex h-screen pt-10"}
      [static-sidebar]
      [:div {:class "flex w-0 flex-1 overflow-hidden"}
      ;[stages/modal]
      ;[user-menu]
       [:main {:id "main" :tab-index -1 :class "flex-1 relative overflow-y-auto focus:outline-none"}
       ;[breadcrumb]
        (:page @current-page)]]]]))
