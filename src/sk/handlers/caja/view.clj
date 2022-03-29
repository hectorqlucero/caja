(ns sk.handlers.caja.view
  (:require [sk.handlers.caja.model
             :refer [get-rows get-rows-consulta get-balances get-docente]]
            [clojurewerkz.money.amounts :as ma]
            [clojurewerkz.money.currencies :as mc]
            [clojurewerkz.money.format :as mf]
            [hiccup.page :refer [include-js]])
  (:import java.util.Locale))

(defn my-body [row]
  [:tr
   [:td (:f_docentes row)]
   [:td (:fecha_formatted row)]
   [:td (:cantidad row)]
   [:td (:cuenta_banco row)]
   [:td (:tipo_movimiento row)]])

(defn caja-view [title]
  (let [rows (get-rows "movimientos")]
    [:table.dg {:data-options "remoteSort:false,fit:true,rownumbers:true,fitColumns:true" :title "Caja"}
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

(defn consulta-view [title docente-id]
  (let [rows (get-rows-consulta docente-id)
        docente (:nombre (get-docente docente-id))
        balances-row (get-balances rows)]
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
                                 :style "margin-left:10px;"} "Procesar"]]
      [:div.container
       [:div.row
        [:div.col
         [:div.card {:style "width: 25rem;"}
          [:div.card-body
           [:h5.card-title.text-success "Balance de caja para: " [:strong.text-primary docente]]
           [:p.card-text
            [:table.table
             [:thead
              [:tr
               [:th "DEPOSITOS"]
               [:th "RETIROS"]
               [:th "BALANCE"]]]
             [:tbody
              [:tr
               [:td (mf/format  (ma/amount-of mc/MXN (:depositos balances-row)) (Locale. "" "MX"))]
               [:td (mf/format (ma/amount-of mc/MXN (:retiros balances-row)) (Locale. "" "MX"))]
               [:td (mf/format (ma/amount-of mc/MXN (:balance balances-row)) (Locale. "" "MX"))]]]]]]]]
        [:div.col
         [:div.card {:style "width: 35rem;"}
          [:div.card-body
           [:h5.card-title.text-success "Balance desglosado para: " [:strong.text-primary docente]]
           [:p.card-text
            [:table.table
             [:thead
              [:tr
               [:th "FECHA"]
               [:th "DEPOSITOS"]
               [:th "RETIROS"]]]
             [:tbody
              (map (fn [row]
                     (list
                      [:tr
                       [:td (:fecha_formatted row)]
                       [:td (mf/format (ma/amount-of mc/MXN (:deposito row)) (Locale. "" "MX"))]
                       [:td (mf/format (ma/amount-of mc/MXN (:retiro row)) (Locale. "" "MX"))]])) rows)]]]]]]]]])))

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
  (consulta-view "Consultas" 1))
