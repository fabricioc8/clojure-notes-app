(ns xiana-experiment-flexiana.core
  (:require
    [xiana-experiment-flexiana.interceptors.default-view :as default-view]
    [xiana-experiment-flexiana.routes :as r]
    [xiana.config :as config]
    [xiana.db :as db]
    [xiana.interceptor :as interceptors]
    [next.jdbc.result-set]
    [xiana.rbac :as rbac]
    [xiana.route :as routes]
    [xiana.session :as session]
    [xiana.webserver :as ws]
    [xiana.commons :refer [rename-key]]))

(defn ->system
  [app-cfg]
  (-> (config/config app-cfg)
      (rename-key :framework.app/auth :auth)
      routes/reset
      rbac/init
      session/init-backend
      db/connect
      db/migrate!
      ws/start))

(def controller-interceptors
  [(interceptors/muuntaja)
   interceptors/params

   session/guest-session-interceptor
   #_session/interceptor

   interceptors/view
   default-view/interceptor
   interceptors/side-effect
   db/db-access
   rbac/interceptor])

(def app-cfg
  {:routes r/routes
   :router-interceptors     []
   :controller-interceptors controller-interceptors
   :xiana/jdbc-opts         {:builder-fn next.jdbc.result-set/as-unqualified-kebab-maps}})

(defn -main
  [& _args]
  (->system app-cfg))
