(ns xiana-experiment-flexiana.interceptors
  (:require
   [re-frame.core :as rf]))

(rf/reg-global-interceptor
 (rf/->interceptor
  :id :auth-interceptor
  :after (fn [context]
           (let [session-id (-> context :coeffects :db :session :session-id)]
             (prn "INTER context" context "SESSIONID" session-id)
             (update-in context
                        [:effects :http-xhrio :headers]
                        assoc :session-id session-id)))))

;; {:coeffects {:event          [:xiana-experiment-flexiana.events.init/initialize-db]
;;              :original-event [:xiana-experiment-flexiana.events.init/initialize-db]
;;              :db             {}}
;;  :stack     '({:id     :auth-interceptor
;;                :before nil
;;                :after  #object[Function]}
;;               {:id     :fx-handler
;;                :before #object[re_frame$std_interceptors$fx_handler__GT_interceptor_$_fx_handler_before]
;;                :after  nil}
;;               {:id     :fx-handler
;;                :before #object[re_frame$std_interceptors$fx_handler__GT_interceptor_$_fx_handler_before]
;;                :after  nil}
;;               {:id     :auth-interceptor
;;                :before nil
;;                :after  #object[Function]}
;;               {:id     :inject-global-interceptors
;;                :before #object[re_frame$std_interceptors$inject_global_interceptors_before]
;;                :after  nil}
;;               {:id     :do-fx
;;                :before nil
;;                :after  #object[re_frame$fx$do_fx_after]}
;;               {:id     :coeffects
;;                :before #object[re_frame$cofx$coeffects_before]
;;                :after  nil})
;;  :effects   {:db {:login-email-field   nil
;;                   :login-status        nil
;;                   :active-remember-me? false
;;                   :session             {:user-id    "d9530872-2958-4784-bad7-1feaa984d53e"
;;                                         :session-id nil}}}
;;  :queue     #queue [{:id     :inject-global-interceptors
;;                      :before #object[re_frame$std_interceptors$inject_global_interceptors_before]
;;                      :after  nil}
;;                     {:id     :do-fx
;;                      :before nil
;;                      :after  #object[re_frame$fx$do_fx_after]}
;;                     {:id     :coeffects
;;                      :before #object[re_frame$cofx$coeffects_before]
;;                      :after  nil}]}
