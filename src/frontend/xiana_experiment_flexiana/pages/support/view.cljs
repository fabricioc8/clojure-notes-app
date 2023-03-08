(ns xiana-experiment-flexiana.pages.support.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]))

(defn page []
  [:div "Support page"])

(defmethod routing/resolve-view :tickets [_] [page])
