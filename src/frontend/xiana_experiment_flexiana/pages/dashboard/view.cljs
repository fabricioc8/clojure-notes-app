(ns xiana-experiment-flexiana.pages.dashboard.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.pages.dashboard.routing]))

(defn page []
  [:div "Dashboard page"])

(defmethod routing/resolve-view :dashboard [_] [page])
