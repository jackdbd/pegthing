(defproject pegthing "0.1.0-SNAPSHOT"
  :description "My implementation of the Pegthing game in Clojure for the Brave and True"
  :url "https://github.com/jackdbd/pegthing"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :pedantic? :warn
  
  :target-path "target/%s"
  :main ^:skip-aot pegthing.core
  
  ;; Java reflection can be bad for performance.
  ;; https://clojure.org/reference/java_interop
  :global-vars {*warn-on-reflection* false}
  
  :aliases {"play" ["with-profile" "dev" "run" "-m" "pegthing.core/-main"]
            "test-all" ["with-profile" "1.9:+1.10" "test"]}
  
  :profiles {:1.9  {:dependencies [[org.clojure/clojure "1.9.0"]]}
             :1.10  {:dependencies [[org.clojure/clojure "1.10.1"]]}
             :uberjar {:aot :all :uberjar-name "pegthing-standalone.jar"}

             ;; composite profiles
             :dev [:project/dev :profile/dev]
             :test [:project/dev :project/test :profile/test]

             ;; subprofiles for composite profiles
             :profile/dev {}
             :profile/test {}
             :project/dev {:dependencies [[io.aviso/pretty "0.1.37" :exclusions [org.clojure/spec.alpha org.clojure/clojure org.clojure/core.specs.alpha]]]
                           :middleware [io.aviso.lein-pretty/inject]
                           :plugins [[com.jakemccrary/lein-test-refresh "0.24.1"]
                                     [io.aviso/pretty "0.1.37"]
                                     [jonase/eastwood "0.3.10"]
                                     [lein-cljfmt "0.6.7" :exclusions [org.clojure/clojure]]]}
             :project/test {:dependencies [[pjstadig/humane-test-output "0.10.0"]]
                            :injections [(require 'pjstadig.humane-test-output)
                                         (pjstadig.humane-test-output/activate!)]}})
