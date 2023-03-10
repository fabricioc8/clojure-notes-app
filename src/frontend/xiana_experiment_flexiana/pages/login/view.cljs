(ns xiana-experiment-flexiana.pages.login.view
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.pages.login.subs :as login-subs]
   [xiana-experiment-flexiana.pages.login.events :as login-events]
   [xiana-experiment-flexiana.events.login :as events-login]))


(defn valid-email? [email]
  (re-seq #"^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$" email))

(defn valid-password? [password]
  (< 8 (count password)))

(defn page []
  (let [email-input-value @(rf/subscribe [::login-subs/sign-up-email-input])
        password-input-value @(rf/subscribe [::login-subs/sign-up-password-input])] 
    [:div {:class "flex"}
     [:div {:class "flex min-h-full flex-col justify-center py-12 sm:px-6 lg:px-8 w-64"}
      [:div {:class "mt-8 sm:mx-auto sm:w-full sm:max-w-md"}
       [:div {:class "bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10"}
        [:form {:class  "space-y-6"
                :action "#"
                ;:method "POST"
                :autoComplete "do-not-autofill"
                :on-submit #(do (.preventDefault %)
                                (prn "SUB" email-input-value password-input-value)
                                (when (and (valid-email? email-input-value)
                                           (valid-password? password-input-value))
                                  (rf/dispatch [::events-login/sign-up email-input-value password-input-value])
                                  (rf/dispatch [::login-events/sign-up-email-input email-input-value])
                                  (rf/dispatch [::login-events/sign-up-password-input password-input-value])))}
         [:div
          [:label {:for   "email"
                   :class "block text-sm font-medium leading-6 text-gray-900"} "Email address"]
          [:div {:class "mt-2"}
           [:input {:class        "block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                    :id           "email"
                    :name         "email"
                    :type         "email"
                    :max-length   200
                   ;:autoComplete "off" #_"do-not-autofill"
                    :required     true
                    :value        (or email-input-value "")
                    :on-change    #(rf/dispatch [::login-events/sign-up-email-input (-> % .-target .-value)])}]]]
         [:div
          [:label {:for   "password"
                   :class "block text-sm font-medium leading-6 text-gray-900"} "Password"]
          [:div {:class "mt-2"}
           [:input {:class        "block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                    :id           "password"
                    :name         "password"
                    :type         "password"
                    :max-length   256
                   ;:autoComplete "do-not-autofill"
                    :required     true
                    :value (or password-input-value "")
                    :on-change #(rf/dispatch [::login-events/sign-up-password-input (-> % .-target .-value)])}]]]
         [:div {:class "flex items-center justify-between"}
          [:div {:class "flex items-center"}
           [:input {:id    "remember-me"
                    :name  "remember-me"
                    :type  "checkbox"
                    :class "h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-600"}]
           [:label {:for   "remember-me"
                    :class "ml-2 block text-sm text-gray-900"} "Remember me"]]]
         [:div
          [:button {:type  "submit"
                    :disabled (not (and (valid-email? (or email-input-value ""))
                                        (valid-password? (or password-input-value ""))))
                    :class "flex w-full justify-center rounded-md bg-indigo-600 py-2 px-3 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"} "Sign in"]]]]]]]))
