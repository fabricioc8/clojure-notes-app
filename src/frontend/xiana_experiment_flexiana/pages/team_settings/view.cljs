(ns xiana-experiment-flexiana.pages.team-settings.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]))

(defn page []
  [:div "Team settings page"])

(defmethod routing/resolve-view :team-settings [_] [page])
