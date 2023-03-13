(ns xiana-experiment-flexiana.events.init
  (:require
   [re-frame.core :as rf]))

(def initial-db
  {:login-email-field                   nil
   :login-status                        nil
   :active-remember-me?                 false
   :session                             {#_#_#_#_:user {:user-id "d9530872-2958-4784-bad7-1feaa984d53e" ;(storage-> "user-id")
                                                :session-id nil} ;(storage-> "session-id")
                                         :team {:id "3c033fb5-162c-4bb7-b8ea-8a4a26019409"
                                                :name "Team1"}}
   })

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   initial-db))
