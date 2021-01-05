(defproject net.b12n/b12n-gh "1.0.0"
  :description "Create new Github repository from the comfort of your command line; TL;DR; $gh-utils --config ~/Dropbox/config/github.edn --repo my-awesome-idea"
  :url "https://github.com/burinc/b12n-gh"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:dependencies [[lein-binplus "0.6.6"]]}
             :uberjar {:aot :all}}
  :source-paths ["src"]
  :bin {:name "gh-utils"
        :bin-path "~/bin"
        :bootclasspath false}
  :plugins [[lein-binplus "0.6.6"]
            [lein-ancient "0.6.15"]
            [lein-cljfmt "0.7.0"]
            [lein-auto "0.1.3"]]
  :dependencies [[org.clojure/clojure "1.10.2-rc1" :scope "provided"]
                 [org.clojure/tools.cli "1.0.194"]
                 [irresponsible/tentacles "0.6.6"]
                 [clj-commons/fs "1.6.307"]
                 [clj-jgit "1.0.1"]
                 [aero "1.1.6"]]
  :repositories [["jitpack" "https://jitpack.io"]]
  :deploy-repositories [["clojars"  {:sign-releases true
                                     :creds :gpg
                                     :signing {:gpg-key "BB24A1BE3FCE8822"}
                                     :url "https://clojars.org/repo"}]
                        ["snapshots" {:sign-releases true
                                      :creds :gpg
                                      :signing {:gpg-key "BB24A1BE3FCE8822"}
                                      :url "https://clojars.org/repo"}]]
  :main net.b12n.github.utils.core)