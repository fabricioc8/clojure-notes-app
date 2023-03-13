(ns xiana-experiment-flexiana.pages.new-note.view
  (:require
   [xiana-experiment-flexiana.pages.new-note.routing]
   [xiana-experiment-flexiana.routing.core :as routing :refer [url-for]]
   [xiana-experiment-flexiana.events.notes :as events-notes]
   [xiana-experiment-flexiana.pages.new-note.subs :as view-subs]
   [xiana-experiment-flexiana.subs.subscriptions :as subs-subscriptions]
   [xiana-experiment-flexiana.pages.new-note.events :as view-events]
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [re-frame.core :as rf]))

(defn page []
  (let [note-title @(rf/subscribe [::view-subs/note-title-input-edit])
        note-content @(rf/subscribe [::view-subs/note-content-text-area])
        is-public? @(rf/subscribe [::view-subs/is-public?])
        note-id @(rf/subscribe [::view-subs/note-id])
        max-chars @(rf/subscribe [::subs-subscriptions/current-subscription-max-chars])]
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
        :max-lenght (str max-chars)
        :value note-content
        :on-change #(rf/dispatch [::view-events/note-content-text-area
                                  max-chars (-> % .-target .-value)])}]
      [:span {:class "block"}
       (str "Limit: " (count note-content) "/" max-chars)]
      [:span {:class "block"}
       (str "Public: " (if is-public? "YES" "NO"))]
      [tc/primary-button
       {:class "block"
        :content (if is-public?
                   "Set public to NO"
                   "Set public to YES")
        :on-click #(rf/dispatch [::view-events/is-public?])}]
      [tc/primary-button
       {:class "block"
        :content "Update note"
        :on-click #(when (and (seq note-title)
                              (< (count note-content) max-chars))
                     (rf/dispatch [::events-notes/update-note note-id
                                   {:name note-title
                                    :content note-content
                                    :is-public is-public?}]))}]]]))

(defmethod routing/resolve-view :new-note [_] [page])
