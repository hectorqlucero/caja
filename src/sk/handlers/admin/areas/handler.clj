(ns sk.handlers.admin.areas.handler
  (:require [sk.models.crud :refer [build-form-row build-form-save build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.layout :refer [application]]
            [sk.models.util :refer [get-session-id user-level]]
            [sk.handlers.admin.areas.view :refer [areas-view areas-scripts]]))

(defn areas [_]
  (let [title "Areas de trabajo"
        ok (get-session-id)
        js (areas-scripts)
        content (areas-view title)]
    (if
     (or
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "solo <strong>los administradores </strong> pueden accessar esta opción!!!"))))

(defn areas-grid
  "builds grid. parameters: params table & args args: {:join 'other-table' :search-extra name='pedro' :sort-extra 'name,lastname'}"
  [{params :params}]
  (let [table "areas"
        args {:sort-extra "descripcion,ciudad"}]
    (build-grid params table args)))

(defn areas-form [id]
  (let [table "areas"]
    (build-form-row table id)))

(defn areas-save [{params :params}]
  (let [table "areas"]
    (build-form-save params table)))

(defn areas-delete [{params :params}]
  (let [table "areas"]
    (build-form-delete params table)))
