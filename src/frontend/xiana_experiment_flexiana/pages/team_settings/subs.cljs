(ns xiana-experiment-flexiana.pages.team-settings.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 ::team-name-input
 (fn [db _]
   (get-in db [:view :team-settings :team-name-input])))
