(ns xiana-experiment-flexiana.pages.new-note.view
  (:require
   [xiana-experiment-flexiana.pages.new-note.routing]
   [xiana-experiment-flexiana.routing.core :as routing :refer [url-for]]
   [xiana-experiment-flexiana.events.notes :as events-notes]
   [xiana-experiment-flexiana.pages.new-note.subs :as view-subs]
   [xiana-experiment-flexiana.pages.new-note.events :as view-events]
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [re-frame.core :as rf]))

(defn page []
  (let [note-title @(rf/subscribe [::view-subs/note-title-input-edit])
        note-content @(rf/subscribe [::view-subs/note-content-text-area])
        is-public? @(rf/subscribe [::view-subs/is-public?])
        note-id @(rf/subscribe [::view-subs/note-id])] 
    [:div {:class "p-6"}
     [:span {:class "text-xl font-bold"}
      "New note"]
     [:div {:class "flex-col space-y-4"}
      [:div {:class "py-1 max-w-max"}
       [tc/basic-field-input {:type "text"
                              :placeholder "Title..."
                              :value note-title
                              :on-change #(rf/dispatch [::view-events/note-title-input-edit
                                                        (-> % .-target .-value)])}]]
      [tc/text-area
       {:width "w-1/2"
        :name "note-content"
        :placeholder "Note content..."
        :default-value ""
        :value note-content
        :on-change #(rf/dispatch [::view-events/note-content-text-area
                                  (-> % .-target .-value)])}]
      [tc/primary-button
       {:class "block"
        :content (if is-public?
                   "Set public to NO"
                   "Set public to YES")
        :on-click #(rf/dispatch [::view-events/is-public?])}]
      [tc/primary-button
       {:class "block"
        :content "Update note"
        :on-click #(rf/dispatch [::events-notes/update-note note-id
                                 {:name note-title
                                  :content note-content
                                  :is-public is-public?}])}]]]))

(defmethod routing/resolve-view :new-note [_] [page])