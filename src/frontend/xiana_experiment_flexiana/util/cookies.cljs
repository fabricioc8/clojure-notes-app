(ns xiana-experiment-flexiana.util.cookies
  (:require
   [goog.net.Cookies :refer [SetOptions]]
   [goog.net.cookies]
   [re-frame.core :as rf]))

(defn set-cookie!
  "sets a cookie, following parameters passed in as a map:
   :name - name of the cookie [REQUIRED]
   :value - value of the cookie [REQUIRED]
   :max-age - defaults to -1
   :path - path of the cookie, defaults to \"/\"
   :domain - domain of the cookie, when null the browser will use the full request host name
   :secure? - boolean specifying whether the cookie should only be sent over a secure channel
   :same-site - A keyword of either :strict, :lax, or :none"
  [{cookie-name :name
    :keys       [value max-age path domain secure? same-site]
    :or         {path "/"}}]
  (let [options (doto (SetOptions.)
                  (set! -maxAge (or max-age -1))
                  (set! -path path)
                  (set! -domain domain)
                  (set! -secure (boolean secure?)))]
    (when same-site
      (set! options -sameSite
            (case same-site
              :strict (.-STRICT goog.net.Cookies.SameSite)
              :lax    (.-LAX goog.net.Cookies.SameSite)
              :none   (.-NONE goog.net.Cookies.SameSite))))
    (.set goog.net.cookies (name cookie-name) (str value) options)))

(defn get-cookie
  [k]
  (->> (name k) (.get goog.net.cookies)))

(defn remove-cookie!
  "removes a cookie, optionally for a specific path and/or domain"
  ([k]
   (.remove goog.net.cookies (name k)))
  ([k path domain]
   (.remove goog.net.cookies (name k) path domain)))

(rf/reg-cofx
 ::get
 (fn [coeffects names]
   (->> (reduce #(into %1 {(keyword %2) (get-cookie %2)}) {} names)
        (assoc coeffects ::get))))

(rf/reg-fx
 ::set
 set-cookie!)

(rf/reg-fx
 ::remove
 (fn [{cookie-name :name
       :keys       [path domain]
       :or         {path "/"}}]
   (remove-cookie! (name cookie-name) path domain)))
