(ns sk.handlers.caja.model
  (:require [sk.models.crud :refer [Query db]]))

(def get-rows-sql
  "
  SELECT
  CONCAT(d.nombre,' ',d.paterno,' ',d.materno) as f_docentes,
  DATE_FORMAT(m.fecha,'%Y %b %d') as fecha_formatted,
  FORMAT(m.cantidad,2) as cantidad,
  m.cuenta_banco,
  IF(m.tipo_movimiento='D',m.cantidad,0) as deposito,
  IF(m.tipo_movimiento='R',m.cantidad,0) as retiro,
  IF(m.tipo_movimiento='D','Deposito','Retiro') as tipo_movimiento
  FROM movimientos m
  JOIN docentes d on d.id = m.f_docentes
  ORDER BY
  m.fecha desc,d.nombre,d.paterno,d.materno
  ")

(defn get-rows [_]
  (Query db [get-rows-sql]))

(def get-rows-cuenta-sql
  "
  SELECT
  m.cuenta_banco,
  CONCAT(d.nombre,' ',d.paterno,' ',d.materno) as f_docentes,
  DATE_FORMAT(m.fecha,'%Y %b %d') as fecha_formatted,
  FORMAT(m.cantidad,2) as cantidad,
  m.cuenta_banco,
  IF(m.tipo_movimiento='D',m.cantidad,0) as deposito,
  IF(m.tipo_movimiento='R',m.cantidad,0) as retiro,
  IF(m.tipo_movimiento='D','Deposito','Retiro') as tipo_movimiento
  FROM movimientos m
  JOIN docentes d on d.id = m.f_docentes
  WHERE cuenta_banco = ?
  ORDER BY
  m.fecha desc,d.nombre,d.paterno,d.materno
  ")

(defn get-cuenta-rows [cuenta_banco]
  (Query db [get-rows-cuenta-sql cuenta_banco]))

(def get-rows-consulta-sql
  "
  SELECT
  CONCAT(d.nombre,' ',d.paterno,' ',d.materno) as f_docentes,
  m.cuenta_banco,
  DATE_FORMAT(m.fecha,'%Y %b %d') as fecha_formatted,
  FORMAT(m.cantidad,2) as cantidad,
  IF(m.tipo_movimiento='D',m.cantidad,0) as deposito,
  iF(m.tipo_movimiento='R',m.cantidad,0) as retiro,
  m.cuenta_banco,
  IF(m.tipo_movimiento='D','Deposito','Retiro') as tipo_movimiento
  FROM movimientos m
  JOIN docentes d on d.id = m.f_docentes
  WHERE m.f_docentes = ?
  ORDER BY
  m.cuenta_banco,m.fecha,d.nombre,d.paterno,d.materno
  ")

(def get-rows-cuenta-consulta-sql
  "
  SELECT
  CONCAT(d.nombre,' ',d.paterno,' ',d.materno) as f_docentes,
  m.cuenta_banco,
  DATE_FORMAT(m.fecha,'%Y %b %d') as fecha_formatted,
  FORMAT(m.cantidad,2) as cantidad,
  IF(m.tipo_movimiento='D',m.cantidad,0) as deposito,
  iF(m.tipo_movimiento='R',m.cantidad,0) as retiro,
  m.cuenta_banco,
  IF(m.tipo_movimiento='D','Deposito','Retiro') as tipo_movimiento
  FROM movimientos m
  JOIN docentes d on d.id = m.f_docentes
  WHERE m.f_docentes = ? AND cuenta_banco = ?
  ORDER BY
  m.cuenta_banco,m.fecha,d.nombre,d.paterno,d.materno
  ")

(defn get-balances [rows]
  (let [cuenta    (:cuenta_banco (first rows))
        depositos (reduce + (map #(+ (:deposito %)) rows))
        retiros   (reduce + (map #(+ (:retiro %)) rows))
        balance   (- depositos retiros)]
    {:cuenta_banco cuenta
     :depositos depositos
     :retiros   retiros
     :balance   balance}))

(defn cuentas [docente-id]
  (let [records (Query db ["SELECT DISTINCT cuenta_banco FROM movimientos WHERE f_docentes = ?" docente-id])
        resultado (map (fn [x] (:cuenta_banco x)) records)]
    resultado))

(defn get-rows-consulta [docente-id]
  (Query db [get-rows-consulta-sql docente-id]))

(defn get-rows-cuenta-consulta [docente-id cuenta_banco]
  (Query db [get-rows-cuenta-consulta-sql docente-id cuenta_banco]))

(defn get-docente [docente-id]
  (first (Query db ["select CONCAT(nombre,' ',paterno,' ',materno) as nombre from docentes where id = ?" docente-id])))

(comment
  (cuentas 1)
  (get-docente 1)
  (get-rows "movimientos")
  (get-rows nil)
  (get-cuenta-rows "p100")
  (get-rows-consulta 1)
  (get-balances (get-rows-cuenta-consulta 1 "p100abc"))
  (get-balances (get-rows-consulta 1)))
