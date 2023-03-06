(ns xiana-experiment-flexiana.controllers.content-pages
  (:require
   [xiana-experiment-flexiana.models.content-pages :as model]
   [xiana-experiment-flexiana.views.content-pages :as view]))

(defn insert-content-page [{{params :body-params} :request :as state}]
  (-> state
      (assoc :db-queries (model/insert-content-page params))
      (assoc :view view/content-pages)))

(defn select-content-page [state]
  (let [content-page-id (-> state :request-data :match :path-params :content-page-id)]
    (-> state
        (assoc :db-queries (model/select-content-page content-page-id))
        (assoc :view view/content-pages))))

(defn select-all-content-pages [state]
  (-> state
      (assoc :db-queries (model/select-all-content-pages))
      (assoc :view view/content-pages)))

(defn update-content-apge [{{params :body-params} :request :as state}]
  (let [content-page-id (-> state :request-data :match :path-params :content-page-id)]
    (-> state
        (assoc :db-queries (model/update-content-apge content-page-id params))
        (assoc :view view/content-pages))))

(defn delete-content-page [state]
  (let [content-page-id (-> state :request-data :match :path-params :content-page-id)]
    (-> state
        (assoc :db-queries (model/delete-content-page content-page-id))
        (assoc :view view/content-pages))))
