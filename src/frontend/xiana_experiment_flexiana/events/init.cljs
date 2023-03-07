(ns xiana-experiment-flexiana.events.init
  (:require
   [re-frame.core :as rf]))

(defn initial-db []
  {:login-email-field                   nil
   :login-status                        nil
   :active-remember-me?                 false
;;    :session                             {:user-id      (storage-> "user-id")
;;                                          :session-id   (storage-> "session-id")}
   })

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   (let [_ (prn "INI")]
     initial-db)))
