(ns xiana-experiment-flexiana.interceptors.session
  (:require
   [clojure.string :as str]
   [xiana-experiment-flexiana.common :as c]
   [xiana-experiment-flexiana.views.common :as response]
   [buddy.sign.jwt :as jwt]))

(def api-token-session
  {:name ::api-token-session
   :enter (fn [state]
            (let [cookies (-> state :request :headers :cookie)
                  cookies-map (when cookies (c/cookies->map cookies))]
              (if (and (:api-token cookies-map) (jwt/unsign (:api-token cookies-map) "secret"))
                (-> state
                    ;(assoc-in [:request :cookies->map] cookies-map)
                    (assoc-in [:session-data :users/role] (-> cookies-map :session-data :role)))
                (response/not-allowed state))))})
;agregar error invalid token.
