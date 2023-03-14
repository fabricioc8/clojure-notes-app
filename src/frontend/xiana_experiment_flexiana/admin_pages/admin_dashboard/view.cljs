(ns xiana-experiment-flexiana.admin-pages.admin-dashboard.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.admin-pages.admin-dashboard.routing]
   [xiana-experiment-flexiana.subs.users :as subs-users]
   [xiana-experiment-flexiana.subs.teams :as subs-teams]
   [xiana-experiment-flexiana.subs.notes :as subs-notes]
   [re-frame.core :as rf]))

(defn card [value title]
  [:div {:class "bg-[#3666A7] h-64 grow basis-0 flex-col flex text-center justify-center"}
   [:span {:class "text-3xl text-white"}
    value]
   [:span {:class "text-3xl text-white"}
    title]])

(defn page []
  (let [all-users @(rf/subscribe [::subs-users/all-users])
        all-teams @(rf/subscribe [::subs-teams/all-teams])
        all-notes @(rf/subscribe [::subs-notes/all-notes])]
    [:div {:class "p-6"}
     [:span {:class "text-xl font-bold"}
      "Dashboard"]
     [:div {:class "w-full flex justify-between gap-11 p-6"}
      [card "-" "Montly income"]
      [card (count all-users) "Users"]
      [card (count all-teams) "Teams"]]
     [:div {:class "w-full flex justify-between gap-11 p-6"}
      [card "-" "Total purchases"]
      [card (count all-notes) "Notes created"]
      [card "-" "Max possible notes"]]]))
     

(defmethod routing/resolve-view :admin-dashboard [_] [page])
