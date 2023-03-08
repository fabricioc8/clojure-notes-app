(ns xiana-experiment-flexiana.admin-pages.admin-support.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]))

(defn page []
  [:div "Admin support page"])

(defmethod routing/resolve-view :admin-tickets [_] [page])
