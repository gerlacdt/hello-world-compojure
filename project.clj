(defproject hello-world "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.0"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter  "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-json "0.4.0"]
                 [cheshire "5.6.1"]
                 [org.clojure/tools.nrepl "0.2.12"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler hello-world.handler/app
         :nrepl {:start? true
                 :port 9998}}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]
                        [org.clojure/tools.trace "0.7.9"]]}
   :user {:plugins [[cider/cider-nrepl "0.11.0-SNAPSHOT"]]}
   :repl {:plugins [[cider/cider-nrepl "0.11.0-SNAPSHOT"]]}})
