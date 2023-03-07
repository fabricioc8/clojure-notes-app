(ns xiana-experiment-flexiana.events.init
  (:require
   [re-frame.core :as rf]))

(def initial-db
  {:login-email-field                   nil
   :login-status                        nil
   :active-remember-me?                 false
;;    :session                             {:user-id      (storage-> "user-id")
;;                                          :session-id   (storage-> "session-id")}
   })

(rf/reg-event-fx
 ::initialize-db
 (fn [_ _]
   {:db initial-db}))
