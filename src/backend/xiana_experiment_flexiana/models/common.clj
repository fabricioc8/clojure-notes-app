(ns backend.xiana-experiment-flexiana.models.common
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
