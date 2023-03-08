(ns xiana-experiment-flexiana.admin-pages.admin-users.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]))

(defn page []
  [:div "Admin users page"])

(defmethod routing/resolve-view :admin-users [_] [page])
