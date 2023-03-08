(ns xiana-experiment-flexiana.pages.billing.view
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]))

(defn page []
  [:div "Billing page"])

(defmethod routing/resolve-view :billing [_] [page])
