(ns xiana-experiment-flexiana.interceptors.session
  (:require
   [clojure.string :as str]
   [xiana-experiment-flexiana.views.common :as response]
   [buddy.sign.jwt :as jwt]))

(defn cookies->map [cookies]
  (let [separate-cookies (clojure.string/split cookies #"; ")]
    (reduce merge
            {}
            (map (fn [cookie]
                   (loop [c (reverse (drop-last 2 (clojure.string/split cookie #"=")))
                          pair (clojure.walk/keywordize-keys
                                (into {}
                                      [(vec
                                        (take-last 2 (clojure.string/split cookie #"=")))]))
                          res pair]
                     (cond
                       (empty? c) res
                       :else (recur (rest c)
                                    nil
                                    {(keyword (first c)) res}))))
                 separate-cookies))))

(def api-token-session
  {:name ::api-token-session
   :enter (fn [state]
            (let [cookies (-> state :request :headers :cookie)
                  cookies-map (when cookies (cookies->map cookies))]
              (if (and (:api-token cookies-map) (jwt/unsign (:api-token cookies-map) "secret"))
                (assoc-in state [:session-data :users/role] (-> cookies-map :session-data :role))
                (response/not-allowed state))))})
