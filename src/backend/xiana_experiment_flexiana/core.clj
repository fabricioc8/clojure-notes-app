(ns xiana-experiment-flexiana.core
  (:require
    [xiana-experiment-flexiana.interceptors.default-view :as default-view]
    [xiana-experiment-flexiana.routes :as r]
    [xiana.config :as config]
    [xiana.db :as db]
    [xiana.interceptor :as interceptors]
    [xiana-experiment-flexiana.interceptors.session :as token]
    [xiana-experiment-flexiana.interceptors.spa-index :as spa-index]
    [next.jdbc.result-set]
    [tiny-rbac.builder :as b]
    [xiana.cookies :as cookies]
    [xiana.rbac :as rbac]
    [xiana.route :as routes]
    [xiana.session :as session]
    [xiana.webserver :as ws]
    [routes :as fr]
    [xiana.commons :refer [rename-key]]))

(def role-set
  (-> (b/add-resource {} :plans)
      (b/add-action :plans [:get :get-all :post :put])
      (b/add-role [:admin :team-admin :team-editor :team-viewer])
      (b/add-permission :admin :plans [:get :get-all :post :put] :all)
      (b/add-permission :team-admin :plans [:get :post] :all)
      (b/add-permission :team-editor :plans :get :all)
      (b/add-permission :team-viewer :plans :get :all)))
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

(defn- spa-route-root
  "prepends / char to input, if input is a single value.
   if it's a collection, prepends / to first element of the collection."
  [input]
  (let [root (if (coll? input)
               (first input)
               input)]
    (str "/" root)))

(def controller-interceptors
  [(interceptors/muuntaja)
   cookies/interceptor
   interceptors/params

   token/api-token-session
   session/guest-session-interceptor
   #_session/interceptor

   interceptors/view
   default-view/interceptor
   interceptors/side-effect
   db/db-access
   ;rbac/interceptor
   ])

(def app-cfg
  {:routes                  r/routes
   :router-interceptors     [(spa-index/wrap-default-spa-index
                              (->> fr/frontend-routes
                                   keys
                                   (map spa-route-root)))]
   :controller-interceptors controller-interceptors
   :xiana/jdbc-opts         {:builder-fn next.jdbc.result-set/as-unqualified-kebab-maps}
   :role-set                role-set})

(defn -main
  [& _args]
  (->system app-cfg))
