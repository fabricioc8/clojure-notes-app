(ns xiana-experiment-flexiana.pages.dashboard.routing
  (:require
   [xiana-experiment-flexiana.routing.core :as routing]
   [xiana-experiment-flexiana.pages.dashboard.events :as dashboard-events]
   [re-frame.core :as rf]))

(defmethod routing/handle-route :dashboard
  [arg]
  (rf/dispatch [::dashboard-events/note-title-input nil])
  (:action arg))

;opciones:
;usar async-flow aca
;ver de crear un endpoint general para traer todos los datos de una. de donde agarro el id, donde inserto el dispatch?
;usar una url con el user id como /app/:user-id y tomarlos de los argumentos como se hizo en candidates
