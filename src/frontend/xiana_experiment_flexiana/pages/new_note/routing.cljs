(ns xiana-experiment-flexiana.pages.new-note.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.events.subscriptions :as events-subscriptions]
   [xiana-experiment-flexiana.events.plans :as events-plans]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :new-note
  [arg]
  (rf/dispatch [::events-subscriptions/select-current-subscription])
  (rf/dispatch [::events-plans/select-team-plans])
  (:action arg))
