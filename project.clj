(defproject net.b12n/b12n-gh "1.0.0"
  :description
  "Create new Github repository from the comfort of your command line; TL;DR; $gh-utils --config ~/Dropbox/config/github.edn --repo my-awesome-idea"

  :url "https://github.com/burinc/b12n-gh"

  :license
  {:name "Eclipse Public License"
   :url "http://www.eclipse.org/legal/epl-v10.html"}

  :profiles
  {:dev {:dependencies [[lein-binplus "0.6.6"]]}
   :uberjar {:aot :all}}

  :source-paths
  ["src"]

  :bin
  {:name "b12n-gh"
   :bin-path "~/bin"
   :bootclasspath false}

  :plugins
  [[lein-ancient "0.7.0"]
   [lein-auto "0.1.3"]
   [lein-binplus "0.6.6"]
   [lein-cljfmt "0.8.0"]
   [lein-nsorg "0.3.0"]
   [lein-ancient "0.7.0"]]

  :cljfmt
  {:indentation? true
   :remove-surrounding-whitespace? true
   :remove-trailing-whitespace? true
   :remove-consecutive-blank-lines? true
   :insert-missing-whitespace? true
   :align-associative? false
   :split-keypairs-over-multiple-lines? false}

  :check-namespace-decls
  {:source-paths ["src" "test"]}

  :dependencies
  [[org.clojure/clojure "1.10.3" :scope "provided"]
   [org.clojure/tools.cli "1.0.206"]
   [irresponsible/tentacles "0.6.8"]
   [clj-commons/fs "1.6.307"]
   [clj-jgit "1.0.1"]
   [aero "1.1.6"]]

  :repositories
  [["jitpack" "https://jitpack.io"]]

  :deploy-repositories
  [["clojars" {:sign-releases false
                ;;:creds :gpg
                ;;:signing {:gpg-key "BB24A1BE3FCE8822"}
               :url "https://clojars.org/repo"}]
   ["snapshots" {:sign-releases false
                 ;;:creds :gpg
                 ;;:signing {:gpg-key "BB24A1BE3FCE8822"}
                 :url "https://clojars.org/repo"}]]

  :main net.b12n.github.utils.core)
