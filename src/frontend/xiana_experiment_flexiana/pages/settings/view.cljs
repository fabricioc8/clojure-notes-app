(ns xiana-experiment-flexiana.pages.settings.view
  (:require
   [xiana-experiment-flexiana.events.users :as events-users]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.components.tailwind :as tc]
   [xiana-experiment-flexiana.routing.core :as routing]))

(defn valid-password? [password]
  (> (count password) 7))

(defn page []
  (r/with-let [password1 (r/atom "")
               password2 (r/atom "")]
    [:div {:class "p-6"}
     [:span {:class "text-xl font-bold block"}
      "Settings"]
     [:span {:class "block"}
      "Password change (leave empty to do nothing)"]
     [:form {:class  "space-y-6"
             :action "#"
                ;:method "POST"
             :autoComplete "do-not-autofill"
             :on-submit #(do (.preventDefault %)
                             (when (and (valid-password? @password1)
                                        (valid-password? @password2)
                                        (= @password1 @password2))
                               (rf/dispatch [::events-users/update-user {:password @password1}])
                               (reset! password1 "")
                               (reset! password2 "")))}
      [tc/basic-field-input
       {:type "text"
        :placeholder "New password"
        :min-length   8
        :value @password1
        :on-change #(reset! password1 (-> % .-target .-value))}]
      [tc/basic-field-input
       {:type "text"
        :placeholder "Repeat password"
        :min-length   8
        :value @password2
        :on-change #(reset! password2 (-> % .-target .-value))}]
      [tc/primary-button
       {:content "Save"
        :type "submit"}]]]))

(defmethod routing/resolve-view :settings [_] [page])
