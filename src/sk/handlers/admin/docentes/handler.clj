(ns sk.handlers.admin.docentes.handler
  (:require [sk.models.crud :refer [build-form-row build-form-save build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.layout :refer [application]]
            [sk.models.util :refer [get-session-id user-level]]
            [sk.handlers.admin.docentes.view :refer [docentes-view docentes-scripts]]))

(defn docentes [_]
  (let [title "Docentes"
        ok (get-session-id)
        js (docentes-scripts)
        content (docentes-view title)]
    (if
     (or
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "solo <strong>los administradores </strong> pueden accessar esta opción!!!"))))

(defn docentes-grid
  "builds grid. parameters: params table & args args: {:join 'other-table' :search-extra name='pedro' :sort-extra 'name,lastname'}"
  [{params :params}]
  (let [table "docentes"
        args {:sort-extra "nombre,paterno,materno"}]
    (build-grid params table args)))

(defn docentes-form [id]
  (let [table "docentes"]
    (build-form-row table id)))

(defn docentes-save [{params :params}]
  (let [table "docentes"]
    (build-form-save params table)))

(defn docentes-delete [{params :params}]
  (let [table "docentes"]
    (build-form-delete params table)))
