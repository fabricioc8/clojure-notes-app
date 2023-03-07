(ns xiana-experiment-flexiana.routing.dispatcher
  (:require
   [bidi.bidi :as bidi]
   ;[frontend.events.navigation :as nav-events]
   [xiana-experiment-flexiana.pages.home.events :as home-events]
   [xiana-experiment-flexiana.routing.routes :refer [routes dispatch-global-evts] :as r]
   [pushy.core :as pushy]
   [re-frame.core :as rf]))

(defmulti handle-route "route handling multimethod" :action)
(defmethod handle-route :default [arg] (:action arg))

(defmulti resolve-view identity)

(defmethod resolve-view :default
  [_]
  [:div
   "unknown view"])

(defprotocol ViewDispatcher
  "View dispatcher protocol"
  (dispatch-view [this]))

(extend-protocol ViewDispatcher
  cljs.core/Keyword
  (dispatch-view [this]
    (rf/dispatch
     [::home-events/current-page
      {:name      this
       :title     (name this)
       :page (resolve-view this)}]))
  cljs.core/PersistentVector
  (dispatch-view [this]
    (rf/dispatch [::home-events/current-page
                  {:name (first this)
                   :title (name (first this))
                   :page (into (resolve-view (first this))
                               (rest this))}])))

(defn- parse-url [url]
  (bidi/match-route routes url))

(defn create-route-data [handler params]
  {:action handler
   :params params})

(defn dispatch-route
  [{:keys [route-params handler]}]
  ;(rf/dispatch [::nav-events/clear-breadcrumb])
  (let [route-data (create-route-data handler route-params)
        view (handle-route route-data)]
    (dispatch-view view)
    (dispatch-global-evts)))

(def ^:export router (pushy/pushy dispatch-route parse-url))

(defn- app-routes []
  (pushy/start! router))

(defn ^:export init-routes! []
  (rf/clear-subscription-cache!)
  (app-routes))
