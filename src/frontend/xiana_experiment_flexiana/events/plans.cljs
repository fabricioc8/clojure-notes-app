(ns xiana-experiment-flexiana.events.plans
  (:require
   [re-frame.core :as rf]
   [xiana-experiment-flexiana.util.seq :as util]
   [ajax.core :as ajax]))

(rf/reg-event-db
 ::team-selected
 (fn [db [_ response]]
   (let [team-plans (-> response :data :plans)]
     (assoc-in db [:entity :team-plans] team-plans))))

(rf/reg-event-fx
 ::select-team-plans
 (fn [{:keys [db]} _]
   (let [team-id (-> db :session :team-data :team-id)]
     {:http-xhrio {:uri (util/url "/api/team-plans/" team-id)
                   :method :get
                   :response-format (ajax/json-response-format {:keywords? true})
                   :format (ajax/json-request-format)
                   :on-success [::team-selected]
                 ;:on-failure [::http/http-error]
                   }})))

(rf/reg-event-db
 ::all-plans-selected
 (fn [db [_ response]]
   (let [plans (-> response :data :plans)]
     (assoc-in db [:entity :plans] plans))))

(rf/reg-event-fx
 ::select-all-plans
 (fn [_ _]
   {:http-xhrio {:uri "/api/plans"
                 :method :get
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::all-plans-selected]
                 ;:on-failure [::http/http-error]
                 }}))

(rf/reg-event-db
 ::new-plan-inserted
 (fn [db [_ response]]
   (let [plan (-> response :data :plans first)]
     (update-in db [:entity :plans] conj plan))))

(rf/reg-event-fx
 ::insert-new-plan
 (fn [_ [_ params]]
   {:http-xhrio {:uri "/api/plans"
                 :method :post
                 :params params
                 :response-format (ajax/json-response-format {:keywords? true})
                 :format (ajax/json-request-format)
                 :on-success [::new-plan-inserted]
                 ;:on-failure [::http/http-error]
                 }}))
