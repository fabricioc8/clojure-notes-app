(ns xiana-experiment-flexiana.interceptors.default-view
  (:require
   [backend.app.views.common :as views]))

(def interceptor
  ;; Applies `views/default` function to `state` if nor `view` nor
  ;; `response` keys are specified in `state`.
  {:leave
   (fn [{:keys [view response] :as state}]
     (let [f (cond
               (seq response) identity
               view           view
               :else          views/default)]
       (f state)))})
