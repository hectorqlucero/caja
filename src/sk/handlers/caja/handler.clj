(ns sk.handlers.caja.handler
  (:require
   [sk.layout :refer [application]]
   [sk.models.util :refer [user-level get-session-id]]
   [sk.handlers.caja.view
    :refer [caja-view
            caja-scripts
            consulta-view
            consulta-scripts
            consulta-index-view
            consulta-index-scripts]]))

(defn caja [_]
  (let [title "Caja"
        ok (get-session-id)
        js (caja-scripts)
        content (caja-view title)]
    (if
     (or
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "solo <strong>los administradores </strong> pueden accessar esta opción!!!"))))

(defn consulta-index [_]
  (let [title "Consulta"
        ok (get-session-id)
        js (consulta-index-scripts)
        content (consulta-index-view title)]
    (if
     (or
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "solo <strong>los administradores </strong> pueden accessar esta opción!!!"))))

(defn consulta [docente-id]
  (let [title "Consulta"
        ok (get-session-id)
        js (consulta-scripts)
        content (consulta-view title docente-id)]
    (if
     (or
      (= (user-level) "A")
      (= (user-level) "S"))
      (application title ok js content)
      (application title ok nil "solo <strong>los administradores </strong> pueden accessar esta opción!!!"))))

(comment
  (consulta 1))
