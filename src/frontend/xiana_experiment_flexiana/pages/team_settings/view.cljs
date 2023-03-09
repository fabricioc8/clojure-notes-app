(ns xiana-experiment-flexiana.pages.team-settings.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.pages.team-settings.events :as ts-events]
   [xiana-experiment-flexiana.pages.team-settings.subs :as ts-subs]
   [xiana-experiment-flexiana.pages.team-settings.routing]
   [xiana-experiment-flexiana.subs.users :as subs-users]
   [xiana-experiment-flexiana.events.teams :as events-teams]
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
                         :on-click #(when (seq new-team-name)
                                     (rf/dispatch [::events-teams/update-team {:name new-team-name}]))}]]))

(defn team-users-table []
  (let [team-users @(rf/subscribe [::subs-users/team-users])]
    [:table
     [:tbody
      (for [tu team-users]
        ^{:key (random-uuid)}
        [:tr
         [:td (:email tu)]
         [:td (:user-role tu)]])]]))

(defn page []
  (let [team @(rf/subscribe [::subs-users/user-team])]
    [:div {:class "p-6"}
     [:span {:class "text-xl pb-4"};;check
      "Team Settings"]
     [team-name-form (:name team)]
     [team-users-table]]))

(defmethod routing/resolve-view :team-settings [_] [page])
