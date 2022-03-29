(ns sk.handlers.movimientos.model
  (:require [sk.models.crud :refer [Query db]]
            [clojurewerkz.money.amounts :as ma]
            [clojurewerkz.money.currencies :as mc]
            [clojurewerkz.money.format :as mf])
  (:import java.util.Locale))

(defn depositos [docente-id]
  (let [depositos (:depositos
                   (first
                    (Query db ["SELECT SUM(cantidad) AS depositos FROM movimientos WHERE tipo_movimiento = 'D' AND f_docentes = ?" docente-id])))]
    depositos))

(defn retiros [docente-id]
  (let [retiros (:retiros
                 (first
                  (Query db ["SELECT SUM(cantidad) AS retiros FROM movimientos WHERE tipo_movimiento = 'R' AND f_docentes = ?" docente-id])))]
    retiros))

(defn money-format [total]
  (mf/format (ma/amount-of mc/MXN total) (Locale. "es" "MX")))

(defn balance [docente-id]
  (let [depositos (depositos docente-id)
        retiros (retiros docente-id)
        total (- depositos retiros)]
    total))

(defn get-rows [tabla]
  (Query db [(str "select * from " tabla)]))

(comment
  (depositos 1)
  (money-format (depositos 1))
  (retiros 1)
  (money-format (retiros 1))
  (balance 1)
  (money-format (balance 1))
  (get-rows "movimientos"))
