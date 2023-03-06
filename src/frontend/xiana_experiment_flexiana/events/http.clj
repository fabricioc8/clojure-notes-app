(ns frontend.xiana-experiment-flexiana.events.http
  (:require
   ;[frontend.events.toasts :as toast-evts]
   ;[frontend.pages.home.events :as home-evts]
   [re-frame.core :as rf]))

(rf/reg-event-db
 ::http-result
 (fn [db [_ result]]
   (assoc db :http-result result)))

(rf/reg-event-db ::http-no-response identity)

;; Default http event handlers
(rf/reg-event-db :http-no-on-success identity)
(rf/reg-event-db :http-no-on-failure identity)

(defn- http-error
  ([title result] (http-error :error title result))
  ([status title result]
   {:status status
    :title  title
    :message (or
              (-> result :response :message str)
              (-> result :errors))}))

;; (rf/reg-event-fx
;;  ::http-error
;;  (fn [{:keys [db]} [_ result]]
;;    {:db (cond-> (assoc db :http-error result)
;;           (= 401 (:status result)) (update :session dissoc :session-id))
;;     :fx [(case (:status result)
;;            401 [:dispatch [::home-evts/logout]]
;;            403 [:dispatch [::toast-evts/push-toast
;;                            (http-error "You do not have permission to do this action" result)]]
;;            400 [:dispatch [::toast-evts/push-toast
;;                            (http-error "Error" result)]]
;;            [:dispatch [::toast-evts/push-toast
;;                        (http-error "Error" result)]])]}))
