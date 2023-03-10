(ns xiana-experiment-flexiana.pages.dashboard.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing :refer [url-for]]
   [xiana-experiment-flexiana.subs.notes :as subs-notes]
   [xiana-experiment-flexiana.pages.dashboard.subs :as dashboard-subs]
   [xiana-experiment-flexiana.pages.dashboard.routing]
   [xiana-experiment-flexiana.events.notes :as event-notes]
   [xiana-experiment-flexiana.pages.dashboard.events :as dashboard-events]
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [re-frame.core :as rf]))

(defn notes-board []
  (let [notes @(rf/subscribe [::subs-notes/team-notes])]
    [:ul {:role "list", :class "grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3"};check
     (for [{:keys [id name content]} notes]
       ^{:key id}
       [:li {:class "col-span-1 divide-y divide-gray-200 rounded-lg bg-white shadow"}
        [:div {:class "flex w-full items-center justify-between space-x-6 p-6"}
         [:div {:class "flex-1 truncate"}
          [:div {:class "flex items-center space-x-3"}
           [:h3 {:class "truncate text-sm font-medium text-gray-900"} name]]
          [:p {:class "mt-1 truncate text-sm text-gray-500"} content]]]
        [:div
         [:div {:class "-mt-px flex divide-x divide-gray-200"}
          [:div {:class "flex w-0 flex-1"}
           [:button {:class "relative -mr-px inline-flex w-0 flex-1 items-center justify-center gap-x-3
                             rounded-bl-lg border border-transparent py-4 text-sm font-semibold text-gray-900"
                     :on-click #(rf/dispatch [::event-notes/delete-note id])}
            "Delete"]]
          [:div {:class "-ml-px flex w-0 flex-1"}
           [:button {:class "relative inline-flex w-0 flex-1 items-center justify-center gap-x-3
                             rounded-br-lg border border-transparent py-4 text-sm font-semibold text-gray-900"
                     :on-click #(rf/dispatch [:navigate (url-for :new-note
                                                                 :note-id id)])}
            "Edit"]]]]])]))

(defn note-form []
  (let [note-title @(rf/subscribe [::dashboard-subs/note-title-input])]
    [:div {:class "py-1 max-w-max flex gap-2"}
     [tc/basic-field-input {:type "text"
                            :placeholder "Title..."
                            :value note-title
                            :on-change #(rf/dispatch [::dashboard-events/note-title-input (-> % .-target .-value)])}]
     [tc/primary-button {:content "Create new note"
                         :on-click #(when (seq note-title)
                                      (prn "Navigate..."))}]]))

(defn page []
  [:div {:class "p-3"}
   [:span {:class "text-xl font-bold"}
    "New note"]
   [note-form]
   [:span {:class "text-xl font-bold"}
    "Team notes"]
   [notes-board]])

(defmethod routing/resolve-view :dashboard [_] [page])


