(ns xiana-experiment-flexiana.pages.team-settings.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.pages.team-settings.events :as ts-events]
   [xiana-experiment-flexiana.pages.team-settings.subs :as ts-subs]
   [xiana-experiment-flexiana.pages.team-settings.routing]
   [xiana-experiment-flexiana.subs.users :as subs-users]
   [xiana-experiment-flexiana.subs.subscriptions :as subs-subscriptions]
   [xiana-experiment-flexiana.events.teams :as events-teams]
   [xiana-experiment-flexiana.events.users :as events-users]
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [re-frame.core :as rf]))

(defn team-name-form [team-name]
  (let [new-team-name @(rf/subscribe [::ts-subs/team-name-input])]
    [:div {:class "py-1 max-w-max flex gap-2"}
     [tc/basic-field-input {:type "text"
                            :name "team-name"
                            :placeholder "Team name..."
                            :value (or new-team-name team-name)
                            :on-change #(rf/dispatch [::ts-events/team-name-input (-> % .-target .-value)])}]
     [tc/primary-button {:content "Save"
                         :on-click #(when (and (seq new-team-name) (not= new-team-name team-name))
                                     (rf/dispatch [::events-teams/update-team {:name new-team-name}]))}]]))

(defn team-users-table []
  (let [team-users @(rf/subscribe [::subs-users/team-users])
        current-subscription @(rf/subscribe [::subs-subscriptions/select-current-subscription])]
    [:<>
     [:span {:class "text-xl"}
      (str "Team (" (count team-users) "/" (:max-users current-subscription) ")")]
     [:table
      [:tbody
       (for [tu team-users]
         ^{:key (:id tu)}
         [:tr
          [:td (:email tu)]
          [:td (:team-role tu)]])]]]))

(defn invite-user-form [team-id]
  (let [team-roles ["a" "b" "c"] #_@(rf/subscribe [])
        default-team-role "a"
        new-user-email @(rf/subscribe [::ts-subs/invite-email-input])
        selected-team-role @(rf/subscribe [::ts-subs/team-role-selector])]
    [:div {:class "py-1 max-w-max flex gap-2"}
     [tc/basic-field-input {:type "email"
                            :name "new-user-email"
                            :placeholder "invited@user.com"
                            :value (or new-user-email "")
                            :on-change #(rf/dispatch [::ts-events/invite-email-input (-> % .-target .-value)])}]
     [tc/selector {:name   "team-role-selector"
                   :values team-roles 
                   :on-change #(rf/dispatch [::ts-events/team-role-selector (-> % .-target .-value)])}]
     [tc/primary-button {:content "Save"
                         :on-click #(when (seq new-user-email)
                                      (rf/dispatch [::events-users/invite-user
                                                    {:email new-user-email
                                                     :team-role (or selected-team-role default-team-role)
                                                     :team-id team-id}]))}]]))

(defn page []
  (let [team @(rf/subscribe [::subs-users/user-team])]
    [:div {:class "p-6"}
     [:span {:class "text-xl pb-4"};;check
      "Team Settings"]
     [team-name-form (:name team)]
     [team-users-table]
     [invite-user-form (:id team)]]))

(defmethod routing/resolve-view :team-settings [_] [page])
