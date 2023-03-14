(ns xiana-experiment-flexiana.admin-pages.admin-users.view
  (:require
   [xiana-experiment-flexiana.admin-pages.admin-users.routing]
   [xiana-experiment-flexiana.routing.core :as routing :refer [url-for]]
   [xiana-experiment-flexiana.events.users :as events-users]
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [xiana-experiment-flexiana.subs.users :as subs-users]
   [re-frame.core :as rf]))

(defn page []
  (let [users @(rf/subscribe [::subs-users/teams-users])]
    [:div {:class "p-6"}
     [:span {:class "text-xl font-bold"}
      "Support"]
     [tc/data-table
      {:titles ["Email" "Team" "Enabled" "Role" "Actions"]
       :items (for [u users]
                ^{:key (random-uuid)}
                [(:email u)
                 (:name u)
                 (if (:user-enabled u) "Enabled" "Disabled")
                 (:user-role u)
                 [tc/primary-button
                  {:content "Edit"
                   :on-click #() #_#(rf/dispatch [:navigate (url-for :edit-user
                                                               :user-id (:user-id u))])}]
                 [tc/primary-button
                  {:content (if (:user-enabled u) "Disable" "Enable")
                   :on-click #(rf/dispatch [::events-users/update-user
                                            {:id (:user-id u)
                                             :enabled (not (:user-enabled u))}])}]
                 (when-not (= (:user-role u) "admin")
                   [tc/primary-button
                    {:content "Make admin"
                     :on-click #(rf/dispatch [::events-users/update-user
                                              {:id (:user-id u)
                                               :user-role "admin"}])}])])}]]))

(defmethod routing/resolve-view :admin-users [_] [page])
