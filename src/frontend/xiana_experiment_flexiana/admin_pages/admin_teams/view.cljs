(ns xiana-experiment-flexiana.admin-pages.admin-teams.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]))

(defn page []
  [:div "Admin teams page"])

(defmethod routing/resolve-view :admin-teams [_] [page])
