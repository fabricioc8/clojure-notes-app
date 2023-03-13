(ns xiana-experiment-flexiana.pages.new-note.view
  (:require
   [xiana-experiment-flexiana.pages.new-note.routing]
   [xiana-experiment-flexiana.routing.core :as routing :refer [url-for]]
   [xiana-experiment-flexiana.pages.new-note.subs :as view-subs]
   [xiana-experiment-flexiana.pages.new-note.events :as view-events]
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [re-frame.core :as rf]))

(defn page []
  (let [note-title @(rf/subscribe [::view-subs/note-title-input-edit])]
    [:div {:class "p-6"}
     [:span {:class "text-xl font-bold"}
      "New note"]
     [:div {:class "py-1 max-w-max flex gap-2"}
      [tc/basic-field-input {:type "text"
                             :placeholder "Title..."
                             :value note-title
                             :on-change #(rf/dispatch [::view-events/note-title-input-edit (-> % .-target .-value)])}]]]))

(defmethod routing/resolve-view :new-note [_] [page])
