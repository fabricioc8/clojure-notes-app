(ns xiana-experiment-flexiana.pages.login.view
  (:require
   [re-frame.core :as rf]
   [reagent.core :as r]
   [xiana-experiment-flexiana.events.login :as events-login]))

(defn valid-email? [email]
  (re-seq #"^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$" email))

(defn valid-password? [password]
  (> (count password) 7))

(defn valid-team-name? [team-name]
  (> (count team-name) 1))

(defn login-section []
  (r/with-let [state (r/atom {:email ""
                              :password ""})]
    [:form {:class  "space-y-6"
            :action "#"
                ;:method "POST"
            :autoComplete "do-not-autofill"
            :on-submit #(do (.preventDefault %)
                            (prn "SUB" (:email @state) (:password @state))
                            (when (and (valid-email? (:email @state))
                                       (valid-password? (:password @state)))
                              (prn "MANDO")
                              ;(rf/dispatch [::events-login/sign-up (:email @state) (:password @state)])
                              (reset! state {:email ""
                                             :password ""})
                              ))}
     [:div
      [:label {:for   "email"
               :class "block text-sm font-medium leading-6 text-gray-900"}
       "Email address"]
      [:div {:class "mt-2"}
       [:input {:class        "block rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300
                                   placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm
                                   sm:leading-6 w-64"
                :id           "email"
                :name         "email"
                :type         "email"
                :max-length   200
                   ;:autoComplete "off" #_"do-not-autofill"
                :required     true
                :value        (or (:email @state) "")
                :on-change    #(swap! state assoc :email (-> % .-target .-value))}]]]
     [:div
      [:label {:for   "password"
               :class "block text-sm font-medium leading-6 text-gray-900"}
       "Password"]
      [:div {:class "mt-2"}
       [:input {:class        "block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset
                               ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600
                               sm:text-sm sm:leading-6"
                :id           "password"
                :name         "password"
                :type         "password"
                :max-length   256
                :min-length   8
                   ;:autoComplete "do-not-autofill"
                :required     true
                :value (or (:password @state) "")
                :on-change #(swap! state assoc :password (-> % .-target .-value))}]]]
     [:div {:class "flex items-center justify-between"}
      [:div {:class "flex items-center"}
       [:input {:id    "remember-me"
                :name  "remember-me"
                :type  "checkbox"
                :class "h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-600"}]
       [:label {:for   "remember-me"
                :class "ml-2 block text-sm text-gray-900"}
        "Remember me"]]]
     [:div
      [:button {:type  "submit"
                :class "flex w-full justify-center rounded-md bg-indigo-600 py-2 px-3 text-sm font-semibold
                        text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2
                        focus-visible:outline-offset-2 focus-visible:outline-indigo-600"}
       "Sign in"]]]))

(defn register-section []
  (r/with-let [state (r/atom {:email ""
                              :password ""
                              :confirm-password ""
                              :team-name ""})]
    (let [valid-data? (and (valid-email? (:email @state))
                           (valid-password? (:password @state))
                           (valid-password? (:confirm-password @state))
                           (= (:password @state) (:confirm-password @state))
                           (valid-team-name? (:team-name @state)))
          _ (prn @state "VD" valid-data?)]
      [:form {:class  "space-y-6"
              :action "#"
                ;:method "POST"
              :autoComplete "do-not-autofill"
              :on-submit #(do (.preventDefault %)
                              (when valid-data?
                                (rf/dispatch [::events-login/sign-up
                                              (:email @state) (:password @state) (:team-name @state)])
                                (reset! state {:email ""
                                               :password ""
                                               :confirm-password ""
                                               :team-name ""})))}
       [:div
        [:label {:for   "email"
                 :class "block text-sm font-medium leading-6 text-gray-900"}
         "Email address"]
        [:div {:class "mt-2"}
         [:input {:class        "block rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300
                                   placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm
                                   sm:leading-6 w-64"
                  :id           "email"
                  :name         "email"
                  :type         "email"
                  :max-length   200
                   ;:autoComplete "off" #_"do-not-autofill"
                  :required     true
                  :value        (or (:email @state) "")
                  :on-change    #(swap! state assoc :email (-> % .-target .-value))}]]]
       [:div
        [:label {:for   "password"
                 :class "block text-sm font-medium leading-6 text-gray-900"}
         "Password"]
        [:div {:class "mt-2"}
         [:input {:class        "block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset
                                 ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600
                                 sm:text-sm sm:leading-6"
                  :id           "password"
                  :name         "password"
                  :type         "password"
                  :max-length   256
                  :min-length   8
                   ;:autoComplete "do-not-autofill"
                  :required     true
                  :value (or (:password @state) "")
                  :on-change #(swap! state assoc :password (-> % .-target .-value))}]]]
       [:div
        [:label {:for   "confirm-password"
                 :class "block text-sm font-medium leading-6 text-gray-900"}
         "Confirm password"]
        [:div {:class "mt-2"}
         [:input {:class        "block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset
                                 ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600
                                 sm:text-sm sm:leading-6"
                  :id           "confirm-password"
                  :name         "confirm-password"
                  :type         "password"
                  :max-length   256
                  :min-length   8
                   ;:autoComplete "do-not-autofill"
                  :required     true
                  :value (or (:confirm-password @state) "")
                  :on-change #(swap! state assoc :confirm-password (-> % .-target .-value))}]]]
       [:div
        [:label {:for   "team-name"
                 :class "block text-sm font-medium leading-6 text-gray-900"}
         "Team name"]
        [:div {:class "mt-2"}
         [:input {:class        "block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset
                                 ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600
                                 sm:text-sm sm:leading-6"
                  :id           "team-name"
                  :name         "team-name"
                  :type         "team-name"
                  :max-length   256
                  :min-length   2
                   ;:autoComplete "do-not-autofill"
                  :required     true
                  :value (or (:team-name @state) "")
                  :on-change #(swap! state assoc :team-name (-> % .-target .-value))}]]]
       [:div {:class "flex items-center justify-between"}
        [:div {:class "flex items-center"}
         [:input {:id    "remember-me"
                  :name  "remember-me"
                  :type  "checkbox"
                  :class "h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-600"}]
         [:label {:for   "remember-me"
                  :class "ml-2 block text-sm text-gray-900"}
          "Remember me"]]]
       [:div
        [:button {:type  "submit"
                  :class (str
                          "flex w-full justify-center rounded-md bg-indigo-600 py-2 px-3 text-sm font-semibold
                          text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2
                          focus-visible:outline-offset-2 focus-visible:outline-indigo-600")}
         "Register"]]])))

(defn reset-password-section []
  (r/with-let [state (r/atom {:email ""})]
    [:form {:class  "space-y-6"
            :action "#"
                ;:method "POST"
            :autoComplete "do-not-autofill"
            :on-submit #(do (.preventDefault %)
                            (prn "SUB" (:email @state) (:password @state))
                            (when (valid-email? (:email @state));;tendria que validar en el endpoint si el email existe sino mostrar error
                              (prn "MANDO")
                              ;(rf/dispatch [::events-login/sign-up (:email @state) (:password @state)])
                              (reset! state {:email ""})
                              ))}
     [:div
      [:label {:for   "email"
               :class "block text-sm font-medium leading-6 text-gray-900"}
       "Email address"]
      [:div {:class "mt-2"}
       [:input {:class        "block rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300
                                   placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm
                                   sm:leading-6 w-64"
                :id           "email"
                :name         "email"
                :type         "email"
                :max-length   200
                   ;:autoComplete "off" #_"do-not-autofill"
                :required     true
                :value        (or (:email @state) "")
                :on-change    #(swap! state assoc :email (-> % .-target .-value))}]]] 
     [:div
      [:button {:type  "submit"
                :class "flex w-full justify-center rounded-md bg-indigo-600 py-2 px-3 text-sm font-semibold
                        text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2
                        focus-visible:outline-offset-2 focus-visible:outline-indigo-600"}
       "Reset password"]]]))

;; [(:email @state) @(rf/subscribe [::login-subs/sign-up-email-input])
;;  (:password @state) @(rf/subscribe [::login-subs/sign-up-password-input])]

(defn page []
  (r/with-let [section (r/atom :login)]
    [:div {:class "flex justify-center"}
     [:div {:class "flex min-h-full flex-col justify-center py-12 sm:px-6 lg:px-8 w-64 w-fit"}
      [:div {:class "mt-8 sm:mx-auto sm:w-full sm:max-w-md"}
       [:div {:class "bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10"}
        [:div {:class "flex justify-center"}
         [:span {:class "cursor-pointer"
                 :on-click #(reset! section :login)}
          "Login"]
         [:span " | "]
         [:span {:class "cursor-pointer"
                 :on-click #(reset! section :register)}
          "Register"]
         [:span " | "]
         [:span {:class "cursor-pointer"
                 :on-click #(reset! section :reset-password)}
          "Reset password"]]
        (case @section
          :login [login-section]
          :register [register-section]
          :reset-password [reset-password-section])]]]]))
