(ns xiana-experiment-flexiana.events.init
  (:require
   [re-frame.core :as rf]))

(def initial-db
  {:login-email-field                   nil
   :login-status                        nil
   :active-remember-me?                 false
   :session                             {:user-id      "d9530872-2958-4784-bad7-1feaa984d53e" ;(storage-> "user-id")
                                         :session-id   nil ;(storage-> "session-id")
                                         }
   })

(rf/reg-event-fx
 ::initialize-db
 (fn [_ _]
   {:db initial-db}))
