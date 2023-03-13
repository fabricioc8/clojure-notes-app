(ns xiana-experiment-flexiana.models.common
  (:require
   [honeysql.core :as sql]
   [honeysql.format :as sqlf])
  (:import
   (java.sql
    Timestamp)
   (java.time
    Instant)
   (java.util
    UUID)))

(defn ->UUID
  [uuid]
  (if (string? uuid)
    (UUID/fromString uuid)
    uuid))

(defn next-uuid
  []
  (UUID/randomUUID))

(defn now
  []
  (Timestamp/from (Instant/now)))

(defn map-keys
  [f m]
  (zipmap (map f (keys m))
          (vals m)))

(defn map-vals
  [f m]
  (zipmap (keys m)
          (map f (vals m))))

(defn normalize-query-param
  "Wraps the input in a coll
  If the input is already a coll, returns it flattened
  If it's an empty string, returns nil
  If it's nil, returns nil"
  [str-or-coll]
  (when (seq str-or-coll)
    (-> []
        (conj str-or-coll)
        flatten)))

(defmethod sqlf/fn-handler "_nest" [_ sentence]
  (str "(" (sqlf/to-sql sentence) ")"))

(defn nest [sentence]
  (sql/call :_nest sentence))

(defn ->user [params]
  (let [params (select-keys params [:id :email :password :enabled :user-role :api-token])]
    (cond-> params
      (:id params) (update :id ->UUID)
      (:team-id params) (update :team-id ->UUID))))

(defn ->team [params]
  (let [params (select-keys params [:id :name :enabled])]
    (cond-> params
      (:id params) (update :id ->UUID))))

(defn ->team-user [params]
  (let [params (select-keys params [:team-id :user-id :team-role])]
    (cond-> params
      (:team-id params) (update :team-id ->UUID)
      (:user-id params) (update :user-id ->UUID))))

(defn ->subscription [params]
  (let [params (select-keys params [:id :team-id :plan-id :canceled])]
    (cond-> params
      (:id params) (update :id ->UUID)
      (:team-id params) (update :team-id ->UUID)
      (:plan-id params) (update :plan-id ->UUID))))

(defn ->note [params]
  (let [params (select-keys params [:id :team-id :is-public :name :content])]
    (cond-> params
      (:id params) (update :id ->UUID)
      (:team-id params) (update :team-id ->UUID))))

(defn ->update-note [params]
  (let [params (select-keys params [:id :is-public :name :content])]
    (cond-> params
      (:id params) (update :id ->UUID))))

(defn ->ticket [params]
  (let [params (select-keys params [:id :team-id :name :resolved])]
    (cond-> params
      (:id params) (update :id ->UUID)
      (:team-id params) (update :team-id ->UUID))))

(defn ->message [params]
  (let [params (select-keys params [:id :ticket-id :user-id :message])]
    (cond-> params
      (:id params) (update :id ->UUID)
      (:ticket-id params) (update :ticket-id ->UUID)
      (:user-id params) (update :user-id ->UUID))))

(defn ->plan [params]
  (let [params (select-keys params [:id :name :price-usd :price-per-user :public
                                    :team-id :max-notes :max-chars :max-users])]
    (cond-> params
      (:id params) (update :id ->UUID)
      (:team-id params) (update :team-id ->UUID))))

(defn ->invoice [params]
  (let [params (select-keys params [:id :team-id :amount-usd :subscription-id])]
    (cond-> params
      (:id params) (update :id ->UUID)
      (:team-id params) (update :team-id ->UUID)
      (:subscription-id params) (update :subscription-id ->UUID))))
