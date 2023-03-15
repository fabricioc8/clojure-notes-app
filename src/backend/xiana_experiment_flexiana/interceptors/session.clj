(ns xiana-experiment-flexiana.interceptors.session
  (:require
   [clojure.string :as str]
   [xiana-experiment-flexiana.views.common :as response]
   [buddy.sign.jwt :as jwt]))

(def api-token-session
  {:name ::api-token-session
   :enter (fn [state]
            (let [cookie (-> state :request :headers :cookie)]
              (if (and cookie (jwt/unsign (str/replace cookie #"api-token=" "") "secret"))
                state
                (response/not-allowed state))))})

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
