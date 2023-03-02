(ns backend.xiana-experiment-flexiana.common
  (:require
   [clojure.set :refer [rename-keys]]
   [clojure.string :as str]
   [clojure.walk :refer [postwalk]]
   [image-resizer.core :as img-resizer])
  (:import
   (java.io
    ByteArrayInputStream)
   (java.sql
    Timestamp)
   (java.time
    Instant)
   (java.time.temporal
    ChronoUnit)))

(defn map-keys
  [f m]
  (zipmap (map f (keys m))
          (vals m)))

(defn map-vals
  [f m]
  (zipmap (keys m)
          (map f (vals m))))

(defn inverse-map
  [m]
  (zipmap (vals m)
          (keys m)))

(defn seq->map
  [v fk fv]
  (zipmap (map fk v)
          (map fv v)))

(defn now ^Instant [] (Instant/now))

(defn plus-seconds ^Instant [^Instant inst ^long seconds]
  (.plus inst seconds ChronoUnit/SECONDS))

(defn ->timestamp ^Timestamp [^Instant inst] (Timestamp/from inst))

(defn fuzzy
  [value]
  (str "%" (str/join "%" (seq value)) "%"))

(defn fuzzy-words
  [value & [{:keys [match-word-starts]}]]
  (str
   (when-not match-word-starts "%")
   (str/join (if match-word-starts "% " "%") (str/split value #" "))
   "%"))

(defn unaccent [s]
  (.replaceAll
   (java.text.Normalizer/normalize s java.text.Normalizer$Form/NFD)
   "[^\\p{ASCII}]"
   ""))

(defn fixed-length-password
  ([] (fixed-length-password 8))
  ([n]
   (let [chars-between #(map char (range (int %1) (inc (int %2))))
         chars (concat (chars-between \0 \9)
                       (chars-between \a \z)
                       (chars-between \A \Z)
                       [\_])
         password (take n (repeatedly #(rand-nth chars)))]
     (reduce str password))))

(defn salt-password [salt password]
  (str salt password))

(defn generate-salt
  ([] (fixed-length-password))
  ([n] (fixed-length-password n)))

(defn image-output-stream->resized-buffered-image
  [image-output-stream width height]
  (-> image-output-stream
      .toByteArray
      ByteArrayInputStream.
      (img-resizer/resize width height)))

(defn modify-keys
  [m f]
  (rename-keys m (reduce #(assoc %1 %2 (f %2)) {} (keys m))))

(defn replace-in-keyword
  [from to k]
  (let [words (clojure.string/split (name k) from)]
    (->> words
         (str/join to)
         keyword)))

(defn transform-keys
  [f form]
  (postwalk (fn [x] (if (map? x)
                      (modify-keys x f)
                      x))
            form))

(defn map-keyword-snake-case->kebab-case
  [m]
  (transform-keys (partial replace-in-keyword #"_" "-") m))

(defn map-keyword-kebab-case->snake-case
  [m]
  (transform-keys (partial replace-in-keyword #"-" "_") m))

(defn remove-namespace-from-map
  [m]
  (transform-keys #(-> % name keyword) m))
