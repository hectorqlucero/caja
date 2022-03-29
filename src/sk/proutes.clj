(ns sk.proutes
  (:require [compojure.core :refer [GET POST defroutes]]
            [sk.handlers.admin.users.handler :as users]
            [sk.handlers.admin.areas.handler :as areas]
            [sk.handlers.admin.docentes.handler :as docentes]
            [sk.handlers.movimientos.handler :as movimientos]
            [sk.handlers.caja.handler :as caja]))

(defroutes proutes
  ;; Start users
  (GET "/admin/users"  req [] (users/users req))
  (POST "/admin/users" req [] (users/users-grid req))
  (GET "/admin/users/edit/:id" [id] (users/users-form id))
  (POST "/admin/users/save" req [] (users/users-save req))
  (POST "/admin/users/delete" req [] (users/users-delete req))
  ;; End users

  ;; Start areas
  (GET "/admin/areas"  req [] (areas/areas req))
  (POST "/admin/areas" req [] (areas/areas-grid req))
  (GET "/admin/areas/edit/:id" [id] (areas/areas-form id))
  (POST "/admin/areas/save" req [] (areas/areas-save req))
  (POST "/admin/areas/delete" req [] (areas/areas-delete req))
  ;; End areas

  ;; Start docentes
  (GET "/admin/docentes"  req [] (docentes/docentes req))
  (POST "/admin/docentes" req [] (docentes/docentes-grid req))
  (GET "/admin/docentes/edit/:id" [id] (docentes/docentes-form id))
  (POST "/admin/docentes/save" req [] (docentes/docentes-save req))
  (POST "/admin/docentes/delete" req [] (docentes/docentes-delete req))
  ;; End docentes

  ;; Start movimientos
  (GET "/movimientos"  req [] (movimientos/movimientos req))
  (POST "/movimientos" req [] (movimientos/movimientos-grid req))
  (GET "/movimientos/edit/:id" [id] (movimientos/movimientos-form id))
  (POST "/movimientos/save" req [] (movimientos/movimientos-save req))
  (POST "/movimientos/delete" req [] (movimientos/movimientos-delete req))
  ;; End movimientos

  ;; Start caja
  (GET "/caja" req [] (caja/caja req))
  (GET "/consultas" req [] (caja/consulta-index req))
  (GET "/consultas/:id" [id] (caja/consulta id))
  ;; End caja
  )
