(ns xiana-experiment-flexiana.pages.settings.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]))

(defn page []
  [:div "Settings page"])

(defmethod routing/resolve-view :settings [_] [page])
