(defproject ch.deepimpact/cronut "0.2.9-SNAPSHOT"

  :description "Clojure Scheduled Execution via Quartzite"

  :url "https://github.com/DEEP-IMPACT-AG/cronut"

  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v20.html"}

  :plugins [[lein-cljfmt "0.6.6" :exclusions [org.clojure/clojure]]
            [jonase/eastwood "0.3.7" :exclusions [org.clojure/clojure]]
            [lein-kibit "0.1.8" :exclusions [org.clojure/clojure org.clojure/tools.reader]]
            [s3-wagon-private "1.3.4" :exclusions [commons-logging
                                                   com.fasterxml.jackson.core/jackson-core]]]

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.logging "1.1.0"]
                 [org.quartz-scheduler/quartz "2.3.2" :exclusions [org.slf4j/slf4j-api]]]

  :profiles {:dev {:resource-paths ["test-resources"]
                   :dependencies   [[ch.qos.logback/logback-classic "1.2.3"]
                                    [org.clojure/core.async "1.2.603"]]}}

  :deploy-repositories [["releases" {:url "https://clojars.org/repo/" :creds :gpg}]]

  :repositories {"di-releases"  {:url           "s3p://di-mvn-private/releases/"
                                 :no-auth       true
                                 :sign-releases true}
                 "di-snapshots" {:url           "s3p://di-mvn-private/snapshots/"
                                 :no-auth       true
                                 :sign-releases true
                                 :releases      false}}

  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version" "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag"]
                  ["deploy"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]
                  ["vcs" "push"]]

  :aliases {"smoke" ["do" ["clean"] ["check"] ["test"] ["kibit"] ["cljfmt" "check"] ["eastwood"]]}

  :source-paths ["src"]
  :test-paths ["test"]

  :pedantic? :abort)
