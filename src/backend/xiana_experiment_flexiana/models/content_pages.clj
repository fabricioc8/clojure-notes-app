(ns backend.xiana-experiment-flexiana.models.content-pages
  (:require
   [backend.xiana-experiment-flexiana.models.common :refer [->UUID next-uuid] :as mc]
   [honeysql.core :as sql]
   [honeysql.helpers :as sqlh]))

(defn insert-content-page [params]
  (let [new-content-page-id (next-uuid)
        content-page-params (-> params
                                (assoc :id new-content-page-id)
                                (select-keys [:path :content]))]
    {:queries [{:insert-into :content-pages
                :values [content-page-params]}]}))

(defn select-content-page [content-page-id]
  {:queries [{:select [:*]
              :from [:content-pages]
              :where [:= :id (->UUID content-page-id)]}]})

(defn select-all-content-pages []
  {:queries [{:select [:*]
              :from [:content-pages]}]})

(defn update-content-apge [content-page-id params]
  {:queries [{:update :content-pages
              :set (select-keys params [:path :content])
              :where [:= :id (->UUID content-page-id)]}]})

(defn delete-content-page [content-page-id]
  {:queries [{:delete-from :content-pages
              :where [:= :id (->UUID content-page-id)]}]})
