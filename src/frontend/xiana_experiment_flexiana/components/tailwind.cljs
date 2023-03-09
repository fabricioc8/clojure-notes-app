(ns xiana-experiment-flexiana.components.tailwind)

(defn input-label [{:keys [name label]}]
  [:label {:htmlFor name, :class "block text-sm font-medium leading-6 text-gray-900"}
   label])

(defn basic-field-input [{:keys [type name placeholder value on-change]}]
  [:div
   [:input {:type        type
            :name        name
            :id          name
            :class       "block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1
                           ring-inset ring-gray-300placeholder:text-gray-400 focus:ring-2
                           focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            :placeholder placeholder
            :value       value
            :on-change   on-change}]])

(defn primary-button [{:keys [content on-click]}]
  [:button {:type  "button"
            :class "rounded-md bg-white py-2 px-3 text-sm font-semibold text-gray-900
                    shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50"
            :on-click on-click}
   content])

(defn selector [{:keys [name values on-change default-value]}]
  (let [options (map (fn [v] [:option {:value v} v]) values)]
    [:div {:class "w-auto"}
     (into
      [:select {:id           name
                :name         name
                :class    "block w-full rounded-md border-0 py-1.5 pl-3 pr-10 text-gray-900
                           ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-indigo-600 sm:text-sm sm:leading-6"
                :on-change on-change
                :defaultValue default-value}]
      options)]))

(defn data-table [{:keys [titles items]}]
  [:div {:class "pr-4"}
   [:div {:class "my-2 flow-root"}
    [:div {:class "-my-2 -mx-4 overflow-x-auto sm:-mx-6 lg:-mx-8"}
     [:div {:class "inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8"}
      [:div {:class "overflow-hidden shadow ring-1 ring-black ring-opacity-5 sm:rounded-lg"}
       [:table {:class "min-w-full divide-y divide-gray-300"}
        [:thead {:class "bg-gray-50"}
         [:tr
          (for [t titles]
            ^{:key (random-uuid)}
            [:th {:scope "col", :class "px-3 py-3.5 text-left text-sm font-semibold text-gray-900"}
             t])]]
        [:tbody {:class "divide-y divide-gray-200 bg-white"}
         (for [row items]
           ^{:key (random-uuid)}
           [:tr
            (for [cell row]
              ^{:key (random-uuid)}
              [:td {:class "whitespace-nowrap px-3 py-4 text-sm text-gray-500"}
               cell])])]]]]]]])
