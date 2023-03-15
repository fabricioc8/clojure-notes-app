(ns xiana-experiment-flexiana.controller-behaviors.login
  (:require
   [buddy.sign.jwt :as jwt]
   [xiana.db :as db]
   [xiana-experiment-flexiana.views.common :as response]))

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

(defn generate-token [state]
  (let [email (-> state :request :body-params :email)
        user (first (db/execute (-> state :deps :db :datasource)
                                {:select [:u.user-role :tu.team-role]
                                 :from [[:users :u]
                                        [:team-users :tu]]
                                 :where [:and
                                         [:= :u.email email]
                                         [:= :u.id :tu.user-id]]}))
        user-role (if (= "admin" (:user-role user))
                    "admin"
                    (:team-role user))
        api-token (jwt/sign {:api-token email} "secret")]
    (-> state
        (deep-merge {:response {:cookies {:api-token {:value api-token
                                                      :path "/"}
                                          :session-data {:value {:users/role user-role}
                                                         :path "/"}}
                                :headers {"access-control-expose-headers" "Set-Cookie"}}}))))

(defn valid-login? [state]
  (let [incoming-password (-> state :request :body-params :password)
        current-password (-> state :response-data :db-data ffirst :password)]
    (if (= current-password incoming-password)
      (generate-token state)
      (response/not-allowed state))))
