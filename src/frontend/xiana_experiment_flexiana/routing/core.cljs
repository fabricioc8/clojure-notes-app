(ns xiana-experiment-flexiana.routing.core
  (:require
   [xiana-experiment-flexiana.routing.dispatcher :as dispatcher]
   [xiana-experiment-flexiana.routing.events]
   [xiana-experiment-flexiana.routing.fx]
   [xiana-experiment-flexiana.routing.routes :as r]))

(defonce ^:export routes r/routes)
(defonce ^:export url-for r/url-for)
(defonce ^:export init-routes! dispatcher/init-routes!)
(defonce ^:export resolve-view dispatcher/resolve-view)
(defonce ^:export handle-route dispatcher/handle-route)
(defonce ^:export router dispatcher/router)
