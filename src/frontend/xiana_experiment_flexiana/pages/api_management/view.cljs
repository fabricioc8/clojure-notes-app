(ns xiana-experiment-flexiana.pages.api-management.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]))

(defn page []
  [:div "API management page"])

(defmethod routing/resolve-view :api-management [_] [page])
