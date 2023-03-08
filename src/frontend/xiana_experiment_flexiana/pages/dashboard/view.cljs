(ns xiana-experiment-flexiana.pages.dashboard.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.subs.notes :as subs-notes]
   [xiana-experiment-flexiana.pages.dashboard.routing]
   [xiana-experiment-flexiana.events.notes :as notes]
   [re-frame.core :as rf]))

(defn page []
  (rf/dispatch [::notes/select-team-notes "3c033fb5-162c-4bb7-b8ea-8a4a26019409"])
  (let [notes @(rf/subscribe [::subs-notes/team-notes])]
    [:div "Dashboard page"
     [:div {:class "flex-col"}
      (for [n notes]
        ^{:key (:id n)}
        [:p (:content n)])]]))

(defmethod routing/resolve-view :dashboard [_] [page])
