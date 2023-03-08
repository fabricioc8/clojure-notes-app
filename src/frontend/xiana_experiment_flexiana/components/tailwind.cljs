(ns xiana-experiment-flexiana.components.tailwind)

(defn basic-field-input [{:keys [type name label placeholder]}]
  [:<>
   [:label {:htmlFor name, :class "block text-sm font-medium leading-6 text-gray-900"}
    label]
   [:div {:class "mt-2"}
    [:input {:type        type
             :name        name
             :id          name
             :class       "block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1
                           ring-inset ring-gray-300placeholder:text-gray-400 focus:ring-2
                           focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
             :placeholder placeholder}]]])

(defn primary-button [{:keys [content on-click]}]
  [:button {:type      "button"
            :class "rounded-md bg-white py-2 px-3 text-sm font-semibold text-gray-900
                    shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50"
            :on-click on-click}
   content])