(ns xiana-experiment-flexiana.interceptors.session
  (:require
   [buddy.sign.jwt :as jwt]))

;;este interceptor lo voy a usar cuando la sesion ya esta iniciada para checkear el token y
;;devolverlo igual si es valido
;;devolver un error
(def session
  {:name ::api-token-session
   :enter (fn [{{token :cookies} :request :as state}]
            (cond-> state
              (jwt/unsign token "secret") (assoc-in [:response :api-token?] true)))
   :leaver "generate-token?"})

#_(throw (ex-info "Missing session data"
                {:xiana/response
                 {:body {:message "Invalid or missing session"}
                  :status 401}}))

;; (t/now)

;; (t/>> (t/now) (t/new-duration 1 :seconds))
;; (jwt/sign {} "a")

;; (try 
;;   (jwt/unsign "srfwrf" "a")
;;   (catch clojure.lang.ExceptionInfo e (prn "ef")))

;; ;; Define claims with `:exp` key
;; (def claims
;;   {:user 1 :exp (time/plus (time/now) (time/seconds 5))})

;; ;; Serialize and sign a token with previously defined claims
;; (def token (jwt/sign claims "key"))

;; ;; wait 5 seconds and try unsign it

;; (jwt/unsign token "key")
;; ;; => ExceptionInfo "Token is older than :exp (1427836475)"

;; ;; use timestamp in the past
;; (jwt/unsign token "key" {:now (time/minus (time/now) (time/seconds 5))})
;; ;; => {:user 1}
