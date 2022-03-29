(ns sk.handlers.admin.areas.model
  (:require [sk.models.crud :refer [Query db]]))

(defn get-rows [tabla]
  (Query db [(str "select * from " tabla)]))

(comment
  (get-rows "areas"))
