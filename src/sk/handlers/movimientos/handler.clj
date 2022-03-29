(ns sk.handlers.movimientos.handler
  (:require [sk.models.crud :refer [build-form-row build-form-save build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.layout :refer [application]]
            [sk.models.util :refer [get-session-id user-level]]
            [sk.handlers.movimientos.view :refer [movimientos-view movimientos-scripts]]))

(defn movimientos [_]
  (let [title "Movimientos a caja"
        ok (get-session-id)
        js (movimientos-scripts)
        content (movimientos-view title)]
    (if
     (or
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "solo <strong>los administradores </strong> pueden accessar esta opción!!!"))))

(defn movimientos-grid
  "builds grid. parameters: params table & args args: {:join 'other-table' :search-extra name='pedro' :sort-extra 'name,lastname'}"
  [{params :params}]
  (let [table "movimientos"
        args {:sort-extra "fecha desc"}]
    (build-grid params table args)))

(defn movimientos-form [id]
  (let [table "movimientos"]
    (build-form-row table id)))

(defn movimientos-save [{params :params}]
  (let [table "movimientos"]
    (build-form-save params table)))

(defn movimientos-delete [{params :params}]
  (let [table "movimientos"]
    (build-form-delete params table)))
