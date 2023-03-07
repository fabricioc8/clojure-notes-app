(ns xiana-experiment-flexiana.pages.board.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]))

(defn page []
  [:div "Board page"])

(defmethod routing/resolve-view :board [_] [page])
