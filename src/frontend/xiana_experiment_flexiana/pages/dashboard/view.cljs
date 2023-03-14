(ns xiana-experiment-flexiana.pages.dashboard.view
  (:require
   [xiana-experiment-flexiana.pages.dashboard.routing]
   [xiana-experiment-flexiana.routing.core :as routing :refer [url-for]]
   [xiana-experiment-flexiana.events.notes :as event-notes]
   [xiana-experiment-flexiana.pages.dashboard.events :as dashboard-events]
   [xiana-experiment-flexiana.subs.notes :as subs-notes]
   [xiana-experiment-flexiana.subs.users :as subs-users]
   [xiana-experiment-flexiana.subs.plans :as subs-plans]
   [xiana-experiment-flexiana.pages.dashboard.subs :as dashboard-subs]
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [re-frame.core :as rf]))

(defn notes-board [team-notes]
  [:ul {:role "list", :class "grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3"};check
   (for [{:keys [id name content is-public]} team-notes]
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
                   :on-click #(rf/dispatch [::dashboard-events/provide-update-note
                                            {:id id
                                             :name name
                                             :content content
                                             :is-public is-public}])}
          "Edit"]]]]])])

(defn note-form [team-notes]
  (let [note-title @(rf/subscribe [::dashboard-subs/note-title-input])
        team-data @(rf/subscribe [::subs-users/session-team-data])
        current-plan @(rf/subscribe [::subs-plans/current-team-plan])
        reached-max-notes? (>= (count team-notes) (:max-notes current-plan))]
    [:div {:class "py-1 max-w-max flex gap-2"}
     [tc/basic-field-input {:type "text"
                            :placeholder "Title..."
                            :value note-title
                            :on-change #(rf/dispatch [::dashboard-events/note-title-input (-> % .-target .-value)])}]
     [tc/primary-button {:content "Create new note"
                         :disabled reached-max-notes?
                         :on-click #(when (and (seq note-title)
                                               (not reached-max-notes?))
                                      (rf/dispatch [::event-notes/insert-new-note note-title (:team-id team-data)])
                                      (rf/dispatch [::dashboard-events/note-title-input nil]))}]]))

(defn page []
  (let [team-notes @(rf/subscribe [::subs-notes/team-notes])]
    [:div {:class "p-6"}
     [:span {:class "text-xl font-bold"}
      "New note"]
     [note-form team-notes]
     [:span {:class "text-xl font-bold"}
      "Team notes"]
     [notes-board team-notes]]))

(defmethod routing/resolve-view :dashboard [_] [page])




;;como filtrar el dashboard con los tickets que corresponden
;;los tickets los pueden ver el admin, editor y viewer
;;puedo tener todos los tickets en el app-db y filtrar solo los del user-id=>team-id(no sirve por performance, tengo que traer cosas que no voy a usar)
;;tener un endpoint que filtre los tickets en base a el user-id:
;;-se puede hacer mediante la query sql o tal vez a traves de algun sistema de permisos

;;la pregunta es... tengo que tener un endpoint que dependa del user id y solo traer el equipo al que pertenece proveyendo el id en un evento en el
;;front
;;o
;;llamar al enpoint get teams, y el backend sabe que usuario esta haciendo la llamada y la filtra
