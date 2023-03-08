(ns xiana-experiment-flexiana.admin-pages.admin-dashboard.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]))

(defn page []
  [:div "Admin dashboard page"])

(defmethod routing/resolve-view :admin-dashboard [_] [page])
