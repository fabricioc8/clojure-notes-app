(ns xiana-experiment-flexiana.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.events.init :as events]
   [xiana-experiment-flexiana.pages.main :as main]
   [xiana-experiment-flexiana.config :as config]
   [xiana-experiment-flexiana.routing.core :as routing]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (routing/init-routes!)
  (rf/dispatch [::events/session-open?])
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [main/main-panel] root-el)))

(defn init []
  (rf/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
