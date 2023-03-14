(ns xiana-experiment-flexiana.admin-pages.admin-teams.view
  (:require
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [xiana-experiment-flexiana.admin-pages.admin-teams.subs :as view-subs]
   [xiana-experiment-flexiana.admin-pages.admin-teams.routing]
   [xiana-experiment-flexiana.routing.core :as routing :refer [url-for]]
   [re-frame.core :as rf]))

(defn page []
  (let [teams @(rf/subscribe [::view-subs/admin-teams-table])]
   [:div {:class "p-6"}
    [:span {:class "text-xl font-bold"}
     "Teams"]
    [tc/data-table
     {:titles ["UUID" "Name" "Plan" "Members" "Notes(existing/max)" "Actions"]
      :items (for [t teams]
               ^{:key (random-uuid)}
               [(:id t)
                (:name t)
                (:plan t)
                (:members t)
                (str (:existing-notes t) "/" (:max-notes t))
                [tc/primary-button
                 {:content "Open details"
                  :on-click #(rf/dispatch [:navigate (url-for :ticket-chat
                                                              :ticket-id (:id t))])}]])}]]))

(defmethod routing/resolve-view :admin-teams [_] [page])
