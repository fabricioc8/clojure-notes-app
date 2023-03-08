(ns xiana-experiment-flexiana.admin-pages.admin-cms.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]))

(defn page []
  [:div "Admin CMS page"])

(defmethod routing/resolve-view :admin-cms [_] [page])
