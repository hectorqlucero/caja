(ns sk.handlers.admin.docentes.view
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
    {:id "numero_empleado"
     :name "numero_empleado"
     :class "easyui-textbox"
     :prompt "Numero de empleado..."
     :data-options "label:'Numero de empleado:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "curp"
     :name "curp"
     :class "easyui-textbox"
     :prompt "Curp..."
     :data-options "label:'Curp:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "f_area_trabajo"
     :name "f_area_trabajo"
     :class "easyui-combobox"
     :data-options "label:'Area de trabajo:',
                 labelPosition:'top',
                 url:'/table_ref/get_areas',
                 method:'GET',
                 required:true,
                 width:'100%'"})
   (build-field
    {:id "nombre"
     :name "nombre"
     :class "easyui-textbox"
     :prompt "Nombre del docente..."
     :data-options "label:'Nombre:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "paterno"
     :name "paterno"
     :class "easyui-textbox"
     :prompt "Apellido paterno..."
     :data-options "label:'Paterno:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "materno"
     :name "materno"
     :class "easyui-textbox"
     :prompt "Apellido materno..."
     :data-options "label:'Materno:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "antiguedad"
     :name "antiguedad"
     :class "easyui-textbox"
     :prompt "Antiguedad..."
     :data-options "label:'Antiguedad:',
        labelPosition:'top',
        required:true,
        width:'100%'"})))

(defn docentes-view [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/admin/docentes"
    (list
     [:th {:data-options "field:'id',sortable:true,width:100"} "Id"]
     [:th {:data-options "field:'numero_empleado',sortable:true,width:100"} "Numero de empleado"]
     [:th {:data-options "field:'curp',sortable:true,width:100"} "Curp"]
     [:th {:data-options "field:'f_area_trabajo',sortable:true,width:100"} "Area de trabajo"]
     [:th {:data-options "field:'nombre',sortable:true,width:100"} "Nombre"]
     [:th {:data-options "field:'paterno',sortable:true,width:100"} "Paterno"]
     [:th {:data-options "field:'materno',sortable:true,width:100"} "Materno"]
     [:th {:data-options "field:'antiguedad',sortable:true,width:100"} "Antiguedad"]))
   (build-toolbar)
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn docentes-scripts []
  (include-js "/js/grid.js"))
