(ns xiana-experiment-flexiana.admin-pages.admin-billing.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]))

(defn page []
  [:div "Admin billing page"])

(defmethod routing/resolve-view :admin-billing [_] [page])
