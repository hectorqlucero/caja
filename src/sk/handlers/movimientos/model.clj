(ns sk.handlers.movimientos.model
  (:require [sk.models.crud :refer [Query db]]
            [clojurewerkz.money.amounts :as ma]
            [clojurewerkz.money.currencies :as mc]
            [clojurewerkz.money.format :as mf])
  (:import java.util.Locale))

(defn depositos [docente-id]
  (let [depositos (:depositos
                   (first
                    (Query db ["SELECT SUM(IFNULL(cantidad,0)) AS depositos FROM movimientos WHERE tipo_movimiento = 'D' AND f_docentes = ?" docente-id])))]
    depositos))

(defn depositos-cuenta [docente-id cuenta_banco]
  (let [depositos (:depositos
                   (first
                    (Query db ["SELECT SUM(IFNULL(cantidad,0)) AS depositos FROM movimientos WHERE tipo_movimiento = 'D' AND f_docentes = ? AND cuenta_banco = ?" docente-id cuenta_banco])))]
    depositos))

(defn retiros [docente-id]
  (let [retiros (:retiros
                 (first
                  (Query db ["SELECT SUM(IFNULL(cantidad,0)) AS retiros FROM movimientos WHERE tipo_movimiento = 'R' AND f_docentes = ?" docente-id])))]
    (+ retiros 0)))

(defn retiros-cuenta [docente-id cuenta_banco]
  (let [retiros (:retiros
                 (first
                  (Query db ["SELECT SUM(IFNULL(cantidad,0)) AS retiros FROM movimientos WHERE tipo_movimiento = 'R' AND f_docentes = ? AND cuenta_banco = ?" docente-id cuenta_banco])))]
    (+ retiros 0)))

(defn money-format [total]
  (mf/format (ma/amount-of (mc/for-code "MXN") total) (Locale. "" "MX")))

(defn cuentas [docente-id]
  (let [records (Query db ["SELECT DISTINCT cuenta_banco FROM movimientos WHERE f_docentes = ?" docente-id])
        resultado (map (fn [x] (:cuenta_banco x)) records)]
    resultado))

(defn balance [docente-id]
  (let [depositos (depositos docente-id)
        retiros (retiros docente-id)
        total (- depositos retiros)]
    total))

(defn balance-cuenta [docente-id cuenta_banco]
  (let [depositos (depositos-cuenta docente-id cuenta_banco)
        retiros (retiros-cuenta docente-id cuenta_banco)
        total (- depositos retiros)]
    total))

(defn get-rows [tabla]
  (Query db [(str "select * from " tabla)]))

(comment
  (depositos-cuenta 1 "p100abc")
  (retiros-cuenta 1 "p100abc")
  (cuentas 1)
  (depositos 1)
  (money-format (depositos 1))
  (retiros 1)
  (money-format (retiros 1))
  (balance 1)
  (balance-cuenta 1 "p100")
  (money-format (balance 1))
  (get-rows "movimientos"))
