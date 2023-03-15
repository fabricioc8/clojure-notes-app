(ns xiana-experiment-flexiana.interceptors.spa-index
  (:require
   [clojure.string :as str]))

(defn wrap-default-spa-index
  "Intercepts requests to urls that start with `spa-route`
   and replaces the request url with the `spa-route` so the SPA
   index page is served."
  [spa-routes]
  {:enter (fn [state]
            (let [uri (-> state :request :uri)]
              (if (reduce (fn [item acc] (or item acc))
                          (map #(str/starts-with? uri %)
                               (remove #{"/"} spa-routes)))
                (assoc-in state [:request :uri] "/")
                state)))})
