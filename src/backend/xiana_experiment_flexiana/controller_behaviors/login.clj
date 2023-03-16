(ns xiana-experiment-flexiana.controller-behaviors.login
  (:require
   [xiana-experiment-flexiana.common :as c]
   [xiana-experiment-flexiana.views.common :as response]))

(defn valid-password? [state]
  (let [incoming-password (-> state :request :body-params :password)
        current-password (-> state :response-data :db-data ffirst :password)]
    (if (= current-password incoming-password)
      (c/generate-cookies state)
      (response/not-allowed state))))
