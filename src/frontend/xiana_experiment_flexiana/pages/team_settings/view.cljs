(ns xiana-experiment-flexiana.pages.team-settings.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.pages.team-settings.events :as ts-events]
   [xiana-experiment-flexiana.pages.team-settings.subs :as ts-subs]
   [xiana-experiment-flexiana.pages.team-settings.routing]
   [xiana-experiment-flexiana.subs.users :as subs-users]
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
                         :on-click #(prn "btn")}]]))

(defn page []
  (let [team @(rf/subscribe [::subs-users/user-team])]
    [:div {:class "p-6"}
     [:span {:class "text-xl pb-4"};;check
      "Team Settings"]
     [team-name-form (:name team)]]))

(defmethod routing/resolve-view :team-settings [_] [page])
