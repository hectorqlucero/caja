(defproject sk "0.1.0"
  :description "Caja Ahorro"
  :url "https://github.com/hectorqlucero/caja"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [compojure "1.7.0" :exclusions [commons-codec]]
                 [hiccup "1.0.5"]
                 [lib-noir "0.9.9"]
                 [clojurewerkz/money "1.10.0"]
                 [com.draines/postal "2.0.5"]
                 [cheshire "5.11.0"]
                 [clj-pdf "2.6.1" :exclusions [commons-codec]]
                 [ondrs/barcode "0.1.0"]
                 [pdfkit-clj "0.1.7" :exclusions [commons-logging commons-codec]]
                 [cljfmt "0.8.0"]
                 [clj-jwt "0.1.1"]
                 [clj-time "0.15.2"]
                 [date-clj "1.0.1"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [org.clojure/data.codec "0.1.1"]
                 [mysql/mysql-connector-java "8.0.29"]
                 [ragtime "0.8.1"]
                 [ring/ring-core "1.9.5" :exclusions [ring/ring-codec commons-logging commons-codec]]]
  :main ^:skip-aot sk.core
  :aot [sk.core]
  :plugins [[lein-ancient "0.7.0"]
            [lein-pprint "1.3.2"]
            [migratus-lein "0.7.3"]]
  :migratus {:store :database
             :migration-dir "migrations"
             :db ~(get (System/getenv) "DATABASE_URL")}
  :uberjar-name "caja.jar"
  :target-path "target/%s"
  :ring {:handler sk.core
         :auto-reload? true
         :auto-refresh? false}
  :resources-paths ["shared" "resources"]
  :aliases {"migrate" ["run" "-m" "sk.migrations/migrate"]
            "rollback" ["run" "-m" "sk.migrations/rollback"]}
  :profiles {:uberjar {:aot :all}})
