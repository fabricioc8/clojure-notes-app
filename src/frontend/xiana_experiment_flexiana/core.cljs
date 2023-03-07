(ns xiana-experiment-flexiana.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.events.init :as events]
   [xiana-experiment-flexiana.views :as views]
   [xiana-experiment-flexiana.config :as config]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn init []
  (rf/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
