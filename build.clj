(ns build
  "pegthing build script.
   
   clojure -T:build uber"
  (:require
   [clojure.tools.build.api :as b]))

(def lib 'com.github.jackdbd/pegthing.core)
(def version (format "1.2.%s" (b/git-count-revs nil)))
(def class-dir "target/classes")
(def basis (b/create-basis {:project "deps.edn"}))
(def jar-file (format "target/%s-%s.jar" (name lib) version))
(def uber-file (format "target/%s-%s-standalone.jar" (name lib) version))

(defn clean [_]
  (println "Clean build directory")
  (b/delete {:path "target"}))

(defn jar [_]
  (clean nil)
  (println (str "Build " jar-file))
  (b/write-pom {:class-dir class-dir
                :lib lib
                :version version
                :basis basis
                :src-dirs ["src"]})
  (b/copy-dir {:src-dirs ["src" "resources"]
               :target-dir class-dir})
  ;; https://clojure.github.io/tools.build/clojure.tools.build.api.html#var-jar
  (b/jar {:class-dir class-dir
          :jar-file jar-file
          :main 'pegthing.core}))

(defn uber [_]
  (clean nil)
  (println (str "Build " uber-file))
  (b/copy-dir {:src-dirs ["src" "resources"]
               :target-dir class-dir})
  (b/compile-clj {:basis basis
                  :src-dirs ["src"]
                  :class-dir class-dir})
  ;; https://clojure.github.io/tools.build/clojure.tools.build.api.html#var-uber
  (b/uber {:class-dir class-dir
           :uber-file uber-file
           :basis basis
           :main 'pegthing.core}))