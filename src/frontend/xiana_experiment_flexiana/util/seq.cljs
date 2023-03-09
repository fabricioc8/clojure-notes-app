(ns xiana-experiment-flexiana.util.seq
  (:require
   [clojure.core.rrb-vector :as fv]
   [clojure.set :as set]
   [clojure.string :as str]))

(defn update-by
  "Applies f to every element in sequence s which,
   when sent as an argument to c, c returns true."
  [s c f & kv]
  (into (empty s)
        (map #(if (c %)
                (apply f % kv)
                %))
        s))

(defn update-id
  "Applies f to every element in sec which :id matches the given id."
  [sec id f & kv]
  (apply update-by sec (comp #{id} :id) f kv))

(defn remove-record-by-id [coll id]
  (remove #(= (:id %) id) coll))

(defn associate-by
  "Same as clojure.core/group-by except that
   it expects a single element in the grouping,
   i.e. it returns only the first element in the vector.
   Returns a transducer when called with a single argument."
  ([f]
   (map (juxt f identity)))
  ([f coll]
   (into {} (map (juxt f identity)) coll)))

(defn replace-by
  "Takes 2 collections of maps each one containing a :keyword k (eg. :id)
   and replace the entire items in coll1 if any in coll2 with the same k.
   If a k in coll2 doesn't exist in coll1 the item is also added.
   If the collections have items with repeated k in it only first is kept."
  [k coll1 coll2]
  (-> (associate-by k coll1)
      (merge (associate-by k coll2))
      vals
      vec))

(defn seek
  "Returns first item from coll for which (pred item) returns true.
   Returns nil if no such item is present, or the not-found value if supplied."
  {:static true}
  ([pred coll] (seek pred coll nil))
  ([pred coll not-found]
   (reduce (fn [_ x]
             (if (pred x)
               (reduced x)
               not-found))
           not-found coll)))

(defn merge-by [k coll1 coll2]
  (map (fn [item1]
         (merge item1
                (seek (fn [item2]
                        (= (k item1) (k item2)))
                      coll2)))
       coll1))

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

(defn- xor
  "Performs 'symmetric difference' between two sets"
  [set1 set2]
  (set/difference
   (set/union set1 set2)
   (set/intersection set1 set2)))

(defn set-xor
  "Performs exclusive or on any number of sets."
  [& sets]
  (reduce xor #{} sets))

(defn vec-remove
  "Remove item at position pos in vector vec."
  [vec pos]
  (fv/catvec (subvec vec 0 pos)
             (if (and (= (count vec) 1) (zero? pos))
               []
               (subvec vec (inc pos)))))

(defn vec-add
  "Add item item at position pos in vector vec"
  [vec item pos]
  (fv/catvec (subvec vec 0 pos) [item] (subvec vec pos)))

(defn vec-move
  "Move elem from position pos1 to position pos2 in vector vec."
  [vec pos1 pos2]
  (let [elem (nth vec pos1)
        vec  (vec-remove vec pos1)]
    (vec-add vec elem pos2)))

(defn keyed-map
  "Transform seq into keyed map based on provided key computing function. Key
  computing function takes one argument, which will be currently processed
  element of sequence.
  If key computing functions returns the same key for multiple elements of sequence,
  only the last such processed element will be present in the final map.

  Example 1:
  (def v [{:id 1} {:id 2} {:id 3}])

  (keyed-map v :id)
  ;;=> {1 {:id 1}, 2 {:id 2}, 3 {:id 3}}

  Example 2:
  (keyed-map (range 10) (comp keyword str))
  ;; => {:0 0, :4 4, :7 7, :1 1, :8 8, :9 9, :2 2, :5 5, :3 3, :6 6}

  Example 3:
  (def words [{:id 1 :word \"Hello\"} {:id 1 :word \"Goodbye\"} {:id 2 :word \"World\"}])

  (keyed-map v :id)
  ;;=> {1 {:id 1 :word \"Goodbye\"}, 2 {:id 2 :word \"World\"}}
  "
  [xs key-fn]
  (reduce (fn [acc x] (assoc acc (key-fn x) x)) {} xs))

(defn update-map-vals
  "Given a map `m` and a function `f` of 1-argument, return a new map whose keys
  are the result of applying `f` to the values of `m`, mapped in the
  corresponding keys of `m`."
  [m f]
  (reduce-kv #(assoc %1 %2 (f %3)) {} m))

(defn sort-map-vals
  "Given a hash map and a vector of same keywords of the map, produces a vector
   with the values of the map sorted by the order specifed in the vector arg"
  [m order]
  (loop [m m
         o order
         r []]
    (if (empty? o)
      r
      (recur (dissoc m (first o))
             (rest o)
             (vec (conj r ((first o) m)))))))

(defn merge-with-json
  "Returns a function that merges a clojure map with a javascript one.
   It returns as a javascript object"
  [clj-map]
  (fn [js-obj]
    (clj->js (merge (js->clj js-obj :keywordize-keys true)
                    clj-map))))

(defn assoc-in-with-json
  "Returns a function that repeatedly applies an assoc-in over the js object
   taking the keys (vectors with paths) and values (new values) from path-value-map.
   The output is a js object."
  [path-value-map]
  (fn [js-obj]
    (clj->js
     (reduce-kv (fn [init k v]
                  (assoc-in init k v))
                (js->clj js-obj :keywordize-keys true)
                path-value-map))))

(defn safe-includes?
  "Basically clojure.string/includes? but escaping nils and lowercasing input."
  [s substr]
  (str/includes? (str/lower-case (or s "")) (str/lower-case (or substr ""))))

(defn remove-accents
  "remove accented letters"
  [s]
  (-> s
      (.normalize "NFD")
      (str/replace #"[\u0300-\u036f]" "")))

(def url (partial str "http://localhost:3000"))
