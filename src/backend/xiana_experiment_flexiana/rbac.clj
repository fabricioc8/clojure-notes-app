(ns xiana-experiment-flexiana.rbac
  (:require
   [tiny-rbac.builder :as b]))

(def role-set
  (->
   (b/add-role {} [:admin :team-admin :team-editor :team-viewer])

   ;;
   (b/add-resource :notes)
   (b/add-action :notes [:get :get-team :get-all :put :delete])

   (b/add-permission :admin :notes :get :all)
   (b/add-permission :admin :notes :get-team :all)
   (b/add-permission :admin :notes :get-all :all)
   (b/add-permission :admin :notes :put :all)
   (b/add-permission :admin :notes :delete :all)

   (b/add-permission :team-admin :notes :get [:team :public])
   (b/add-permission :team-admin :notes :get-team [:team :public]);;el doble team es para que pueda checkear que el :team-id recibido corresponde al usuario, sino por mas que este restringido al rando de team-id en la tabla un usuario puede mandar un id de otro equipo en el request y ver todo los registros
   (b/add-permission :team-admin :notes :put :team)
   (b/add-permission :team-admin :notes :delete :team)

   (b/add-permission :team-editor :notes :get [:team :public])
   (b/add-permission :team-editor :notes :get-team [:team :public])
   (b/add-permission :team-editor :notes :put :team)
   (b/add-permission :team-editor :notes :delete :team)

   (b/add-permission :team-viewer :notes :get [:team :public])
   (b/add-permission :team-viewer :notes :get-team [:team :public])

   ;;
   (b/add-resource :plans)
   (b/add-action :plans [:get-team :get-all :post :put])

   (b/add-permission :admin :plans :get-team :all)
   (b/add-permission :admin :plans :get-all :all)
   (b/add-permission :admin :plans :post :all)
   (b/add-permission :admin :plans :put :all)

   (b/add-permission :team-admin :plans :get-team [:team :public])
   (b/add-permission :team-admin :plans :post :team)

   (b/add-permission :team-editor :plans :get-team :current)

   (b/add-permission :team-viewer :plans :get-team :current)

   ;;
   (b/add-resource :invoices)
   (b/add-action :invoices [:get :get-all :post])

   (b/add-permission :admin :invoices :get :all)
   (b/add-permission :admin :invoices :get-all :all)
   (b/add-permission :admin :invoices :post :all)
   
   (b/add-permission :team-admin :invoices :get :team)
   (b/add-permission :team-admin :invoices :post :team)))

  