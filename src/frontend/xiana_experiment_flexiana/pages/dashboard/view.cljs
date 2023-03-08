(ns xiana-experiment-flexiana.pages.dashboard.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.subs.notes :as subs-notes]
   [xiana-experiment-flexiana.pages.dashboard.routing]
   [re-frame.core :as rf]))

(defn page []
  (let [notes @(rf/subscribe [::subs-notes/team-notes])]
    [:div "Dashboard page"
     [:div {:class "flex-col"}
      (for [n notes]
        [:p (:notes/content n)])]]))

(defmethod routing/resolve-view :dashboard [_] [page])
