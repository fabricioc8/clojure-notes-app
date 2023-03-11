(ns xiana-experiment-flexiana.controller-behaviors.login
  (:require
   [buddy.sign.jwt :as jwt]))

(defn- deep-merge* [& maps]
  (let [f (fn [old new]
            (if (and (map? old) (map? new))
              (merge-with deep-merge* old new)
              new))]
    (if (every? map? maps)
      (apply merge-with f maps)
      (last maps))))

(defn deep-merge
  "Same as clojure.core/merge, except that
   it recursively applies itself to every nested map.
   Errors out when called with anything other than maps."
  [& maps]
  (let [maps (filter identity maps)]
    (assert (every? map? maps))
    (apply merge-with deep-merge* maps)))

;; (defn- session-id->cookie
;;   [state]
;;   (if-let [session-id (get-in state [:session-data :session-id])]
;;     (assoc-in
;;      (assoc-in state [:response :cookies :session-id] session-id)
;;      [:response :headers "access-control-expose-headers"]
;;      "Set-Cookie")
;;     state))

(defn generate-token [{{db-results :db-data} :response-data :as state}]
  (let [user (ffirst db-results)
        api-token (jwt/sign {:email (:email user)} "secret")]
    (-> state
        (deep-merge {:response {:cookies {:api-token api-token}
                                :headers {"access-control-expose-headers" "Set-Cookie"}}}))))

{:cookies {:api-token "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3QrdGVzdGVyQGZyYW5raWUucHJvNiJ9.6iQuv5s4eidVMqym_snBDb11e-hjI0Pg2PkBCRuD5ds"}
 :headers {"access-control-expose-headers" "Set-Cookie"
           "Content-type"                  "Application/json"}
 :status  200
 :body    {:data {:users [{:id        #uuid "62a3380e-bac6-461a-b72c-ab00cdf5a932"
                           :email     "test+tester@frankie.pro6"
                           :password  "not_nulafefwefwef"
                           :enabled   true
                           :user-role "user"
                           :api-token #uuid "92e8a887-f818-4b9c-ac09-8c5bf3fb6afb"}]}}}
#_(defn generate-token [state]
  ;(prn "RESPONSE" (:response state))
  (assoc-in state [:response :api-token] (jwt/sign {:userid 1} "secret")))

#_{:api-token "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyaWQiOjF9.kHj3dQt4bViKHDCg9AklavdUv0Bdk4ufWdHd-TzYoJY"
 :status    200
 :headers   {"Content-type" "Application/json"}
 :body      {:data {:users [{:id        #uuid "c8e6e8ad-cc31-462e-954a-812affecdf7c"
                             :email     "test+tester@frankie.pro5"
                             :password  "not_nuwefwefwefl"
                             :enabled   true
                             :user-role "user"
                             :api-token #uuid "3c6c0dca-45a3-49c8-ad4b-0ef42ff5040a"}]}}}
;; (jwt/sign {:userid 1} "secret")


#_(def dbr '([{:id #uuid "f7ecbb7a-fc9e-4908-9220-fbcea81776df", :email "test+tester@frankie.pros", :password "not_nuldsfsfefwef", :enabled true, :user-role "user", :api-token #uuid "713410ca-84c7-445d-a24d-587792ac4751"}]
           [{:id #uuid "a1c2b8a0-3e86-4076-8ab7-df8c4fa69193", :name "", :enabled true}]
           [{:team-id #uuid "a1c2b8a0-3e86-4076-8ab7-df8c4fa69193", :user-id #uuid "f7ecbb7a-fc9e-4908-9220-fbcea81776df", :team-role "team-admin"}]
           [{:team-id #uuid "a1c2b8a0-3e86-4076-8ab7-df8c4fa69193", :price-per-user false, :name "Free", :public false, :price-usd 0.00M, :max-notes 3, :max-chars 100, :max-users 2, :id #uuid "1f5846a6-606e-4d77-b7c0-6578b032deae"}]
           [{:id #uuid "84b674be-4310-4903-874c-e482739fde29", :team-id #uuid "a1c2b8a0-3e86-4076-8ab7-df8c4fa69193", :plan-id #uuid "1f5846a6-606e-4d77-b7c0-6578b032deae", :canceled false}]))
