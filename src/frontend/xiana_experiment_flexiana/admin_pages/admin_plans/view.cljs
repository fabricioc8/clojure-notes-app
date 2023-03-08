(ns xiana-experiment-flexiana.admin-pages.admin-plans.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]))

(defn page []
  [:div "Admin plans page"])

(defmethod routing/resolve-view :admin-plans [_] [page])
