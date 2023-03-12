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

(defn generate-token [{{db-results :db-data} :response-data :as state}]
  (let [user (ffirst db-results)
        api-token (jwt/sign {:email (:email user)} "secret")]
    (-> state
        (deep-merge {:response {:cookies {:api-token {:value api-token
                                                      :path "/"}}
                                :headers {"access-control-expose-headers" "Set-Cookie"}}}))))
