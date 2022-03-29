(ns sk.handlers.admin.areas.view
  (:require
   [hiccup.page :refer [include-js]]
   [ring.util.anti-forgery :refer [anti-forgery-field]]
   [sk.models.util :refer
    [build-dialog build-dialog-buttons build-field build-table build-toolbar]]))

(defn dialog-fields []
  (list
   (build-field
    {:id "id"
     :name "id"
     :type "hidden"})
   (build-field
    {:id "descripcion"
     :name "descripcion"
     :class "easyui-textbox"
     :prompt "Descripcion del area de trabajo..."
     :data-options "label:'Descripcíon:',
        labelPosition:'top',
        required:true,
        multiline:true,
        height:120,
        width:'100%'"})
   (build-field
    {:id "ciudad"
     :name "ciudad"
     :class "easyui-textbox"
     :prompt "Ciudad..."
     :data-options "label:'Ciudad:',
        labelPosition:'top',
        required:true,
        width:'100%'"})))

(defn areas-view [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/admin/areas"
    (list
     [:th {:data-options "field:'id',sortable:true,width:100"} "Id"]
     [:th {:data-options "field:'descripcion',sortable:true,width:100"} "Descripcion"]
     [:th {:data-options "field:'ciudad',sortable:true,width:100"} "Ciudad"]))
   (build-toolbar)
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn areas-scripts []
  (include-js "/js/grid.js"))
