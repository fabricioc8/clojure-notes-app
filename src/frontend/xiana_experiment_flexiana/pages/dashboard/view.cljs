(ns xiana-experiment-flexiana.pages.dashboard.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.subs.notes :as subs-notes]
   [xiana-experiment-flexiana.pages.dashboard.routing]
   [xiana-experiment-flexiana.events.notes :as event-notes]
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
                             rounded-br-lg border border-transparent py-4 text-sm font-semibold text-gray-900"}
            "Edit"]]]]])]))

(defn note-form []
  [:div {:class "py-4 max-w-max"}
   [tc/basic-field-input {:type "text" 
                          :name "note-title"
                          :label "Create new note"
                          :placeholder "Title..."}]
   [tc/primary-button {:content "Create new note" 
                       :on-click #(prn "btn")}]])

(defn page []
  [:div {:class "p-3"}
   [note-form]
   [notes-board]])

(defmethod routing/resolve-view :dashboard [_] [page])


