(ns sk.handlers.movimientos.view
  (:require
   [hiccup.page :refer [include-js]]
   [ring.util.anti-forgery :refer [anti-forgery-field]]
   [sk.models.util :refer
    [build-dialog build-dialog-buttons build-field build-table build-toolbar build-image-field build-image-field-script build-radio-buttons]]))

(defn dialog-fields []
  (list
   (build-field
    {:id "id"
     :name "id"
     :type "hidden"})
   (build-image-field)
   (build-field
    {:id "f_docentes"
     :name "f_docentes"
     :class "easyui-combobox"
     :data-options "label:'Docente:',
                 labelPosition:'top',
                 url:'/table_ref/get_docentes',
                 method:'GET',
                 required:true,
                 width:'100%'"})
   (build-field
    {:id "fecha"
     :name "fecha"
     :class "easyui-datebox"
     :prompt "mm/dd/aaaa ex. 02/07/1957 es: Febreo 2 de 1957"
     :data-options "label:'Fecha:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "cantidad"
     :name "cantidad"
     :class "easyui-textbox"
     :prompt "Cantidad"
     :data-options "label:'Cantidad:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-field
    {:id "cuenta_banco"
     :name "cuenta_banco"
     :class "easyui-textbox"
     :prompt "Cuenta del banco etc..."
     :data-options "label:'Cuenta:',
        labelPosition:'top',
        required:true,
        width:'100%'"})
   (build-radio-buttons
    "Tipo de movimiento:"
    (list
     {:id "tipo_movimiento_d"
      :name "tipo_movimiento"
      :class "easyui-radiobutton"
      :value "D"
      :data-options "label:'Deposito',checked:true"}
     {:id "tipo_movimiento_r"
      :name "tipo_movimiento"
      :class "easyui-radiobutton"
      :value "R"
      :data-options "label:'Retiro'"}))))

(defn movimientos-view [title]
  (list
   (anti-forgery-field)
   (build-table
    title
    "/movimientos"
    (list
     [:th {:data-options "field:'f_docentes',sortable:true,width:100"
           :formatter "getDocente"} "Docente"]
     [:th {:data-options "field:'fecha_formatted',sortable:true,width:100"} "Fecha"]
     [:th {:data-options "field:'cantidad',sortable:true,width:100"} "Cantidad"]
     [:th {:data-options "field:'cuenta_banco',sortable:true,width:100"} "Cuenta"]
     [:th {:data-options "field:'tipo_movimiento',sortable:true,width:100"
           :formatter "getTipo"} "Tipo de movimiento"]))
   (build-toolbar)
   (build-dialog title (dialog-fields))
   (build-dialog-buttons)))

(defn movimientos-scripts []
  (list
   [:script
    "
     function getDocente(val, row, index) {
      let result = null;
      let scriptUrl = '/table_ref/get_docentes/' + val;
      $.ajax({
        url: scriptUrl,
        type: 'get',
        dataType: 'html',
        async: false,
        success: function(data) {
          result = data;
        }
      });
      return result;
     }

    function getTipo(val, row, index) {
      let valor = null;
      if(val === 'D') {
        valor = 'Deposito';
      } else if(val === 'R') {
        valor = 'Retiro';
      } else {
        valor = '???';
      }
      return valor;
    }
     "]
   (include-js "/js/grid.js")
   [:script
    (build-image-field-script)]))
