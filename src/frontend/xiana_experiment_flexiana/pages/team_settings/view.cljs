(ns xiana-experiment-flexiana.pages.team-settings.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.components.tailwind :as tc]))

(defn team-name-form []
  [:div {:class "py-1 max-w-max flex gap-2"}
   [tc/basic-field-input {:type "text"
                          :name "team-name"
                          :placeholder "Team name..."
                          :value "xxx"}]
   [tc/primary-button {:content "Save"
                       :on-click #(prn "btn")}]])

(defn page []
  (let []
    [:div {:class "p-6"}
     [:span {:class "text-xl pb-4"};;check
      "Team Settings"]
     [team-name-form]]))

(defmethod routing/resolve-view :team-settings [_] [page])
