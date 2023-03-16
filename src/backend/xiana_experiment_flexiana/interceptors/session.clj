(ns xiana-experiment-flexiana.interceptors.session
  (:require
   [xiana-experiment-flexiana.common :as c]
   [xiana-experiment-flexiana.views.common :as response]
   [buddy.sign.jwt :as jwt]))

(def api-token-session
  {:name ::api-token-session
   :enter (fn [state]
            (let [cookies (-> state :request :headers :cookie)
                  cookies-map (when cookies (c/cookies->map cookies))]
              (if (and (:api-token cookies-map) (jwt/unsign (:api-token cookies-map) "secret"))
                (assoc-in state [:session-data :users/role] (-> cookies-map :session-data :role keyword))
                (response/not-allowed state))))})
