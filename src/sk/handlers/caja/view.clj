(ns sk.handlers.caja.view
  (:require [sk.handlers.caja.model
             :refer [cuentas get-rows get-rows-consulta get-rows-cuenta-consulta get-balances get-docente]]
            [sk.handlers.movimientos.model :refer [money-format]]))

(defn my-body [row]
  [:tr
   [:td (:f_docentes row)]
   [:td (:fecha_formatted row)]
   [:td (:cantidad row)]
   [:td (:cuenta_banco row)]
   [:td (:tipo_movimiento row)]])

(defn caja-view [title]
  (let [rows (get-rows "movimientos")]
    [:table.dg {:data-options "remoteSort:false,fit:true,rownumbers:true,fitColumns:true" :title title}
     [:thead
      [:tr
       [:th {:data-options "field:'f_docentes',sortable:true,width:100"} "Docente"]
       [:th {:data-options "field:'fecha_formatted',sortable:true,width:100"} "Fecha"]
       [:th {:data-options "field:'cantidad',sortable:true,width:100"} "Cantidad"]
       [:th {:data-options "field:'cuenta_banco',sortable:true,width:100"} "Cuenta"]
       [:th {:data-options "field:'tipo_movimiento',sortable:true,width:100"} "Tipo Movimiento"]]]
     [:tbody (map my-body rows)]]))

(defn caja-scripts []
  [:script
   "
   var dg = $('.dg');
   $(document).ready(function() {
   dg.datagrid();
   dg.datagrid('enableFilter');
   });
   "])

(defn consulta-index-view [title]
  (list
   [:div.col-12.text-center
    [:h3 {:style "color:#158CBA;text-transform:uppercase;font-weight:bold;"} title]
    [:hr]
    [:div.col-12.text-center {:style "margin-bottom:10px;"}
     [:input.easyui-combobox {:name "docentes"
                              :id "docentes_id"
                              :data-options "method:'GET',url:'/table_ref/get_docentes',limitToList:true"}]
     [:button.btn.btn-primary {:id "procesar"
                               :type "button"
                               :style "margin-left:10px;"} "Procesar"]]]))

(defn consulta-index-scripts []
  [:script
   "
   $(document).ready(function() {
   $('#procesar').click(function() {
   var docentes_id = $('#docentes_id').combobox('getValue');
   var url = '/consultas/' + docentes_id;
   window.location.replace(url);
   });
   });
   "])

(defn build-depositos [balances-row]
  [:tr
   [:td {:style "text-align:right;"} [:strong (:cuenta_banco balances-row)]]
   [:td (money-format (:depositos balances-row))]
   [:td (money-format (:retiros balances-row))]
   [:td (money-format (:balance balances-row))]])

(defn process-depositos [docente-id balances-row cuentas cnt]
  (if (> cnt 1)
    (do
      (list
       (map (fn [cuenta]
              (build-depositos (get-balances (get-rows-cuenta-consulta docente-id cuenta)))) cuentas)))
    (build-depositos balances-row)))

(defn consulta-view [title docente-id]
  (let [cuentas      (vec (cuentas docente-id))
        cnt          (count cuentas)
        rows         (get-rows-consulta docente-id)
        docente      (:nombre (get-docente docente-id))
        balances-row (get-balances rows)]
    (list
     [:div.col-12.text-center
      [:h3 {:style "color:#158CBA;text-transform:uppercase;font-weight:bold;"} title]
      [:hr]
      [:div.col-12.text-center {:style "margin-bottom:10px;"}
       [:input.easyui-combobox {:name         "docentes"
                                :id           "docentes_id"
                                :data-options "method:'GET',url:'/table_ref/get_docentes',limitToList:true"}]
       [:button.btn.btn-primary {:id    "procesar"
                                 :type  "button"
                                 :style "margin-left:10px;"} "Procesar"]]
      [:div.container
       [:div.row
        [:div.col
         [:div.card {:style "width: 27rem;"}
          [:div.card-body
           [:h5.card-title.text-success "Balance de caja para: " [:strong.text-primary docente]]
           [:p.card-text
            [:table.table
             [:thead
              [:tr
               [:th "CUENTA"]
               [:th "DEPOSITOS"]
               [:th "RETIROS"]
               [:th "BALANCE"]]]
             [:tbody
              (process-depositos docente-id balances-row cuentas cnt)
              (if (> cnt 1)
                (build-depositos (assoc balances-row :cuenta_banco "Total:")))]]]]]]

        [:div.col
         [:div.card {:style "width: 35rem;"}
          [:div.card-body
           [:h5.card-title.text-success "Balance desglosado para: " [:strong.text-primary docente]]
           [:p.card-text
            [:table.table
             [:thead
              [:tr
               [:th "CUENTA"]
               [:th "FECHA"]
               [:th {:style "text-align:right;"} "DEPOSITO"]
               [:th {:style "text-align:right;"} "RETIRO"]]]
             [:tbody
              (map (fn [row]
                     (list
                      [:tr
                       [:td {:style "text-align:right;font-weight:bold;"} (:cuenta_banco row)]
                       [:td {:style "text-align:right;"} (:fecha_formatted row)]
                       [:td {:style "text-align:right;"} (money-format (:deposito row))]
                       [:td {:style "text-align:right;"} (money-format (:retiro row))]])) rows)
              [:tr
               [:td {:style "text-align:right;"} [:span {:style "font-weight:bold;"} "Total:"]]
               [:td [:span " "]]
               [:td {:style "text-align:right;"} (money-format (:depositos balances-row))]
               [:td {:style "text-align:right;"} (money-format (:retiros balances-row))]]]]]]]]]]])))

(defn consulta-scripts []
  [:script
   "
   $(document).ready(function() {
   $('#procesar').click(function() {
   var docentes_id = $('#docentes_id').combobox('getValue');
   var url = '/consultas/' + docentes_id;
   window.location.replace(url);
   });
   });
   "])

(comment
  (money-format 200)
  (get-rows-consulta 1)
  (consulta-view "Consultas" 1))
