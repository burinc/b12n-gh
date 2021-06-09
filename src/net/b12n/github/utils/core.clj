(ns net.b12n.github.utils.core
  (:gen-class)
  (:require [aero.core :refer [read-config]]
            [clj-jgit.porcelain :as cjp]
            [clojure.java.shell :as shell]
            [clojure.string :as str]
            [clojure.tools.cli :as cli]
            [me.raynes.fs :as fs]
            [net.b12n.github.utils.option :as opt]
            [tentacles.repos :as repos]))

(defn git-init-commit
  "Run initial git commit on a given directory."
  [base-dir]
  (let [git-repo (cjp/git-init base-dir)]
    (cjp/git-add git-repo ".")
    (cjp/git-commit git-repo "Initial commit")
    (cjp/git-status git-repo)))

(defn git-add-remote
  "Run `git remote add origin git@github.com:username/project.git' on the current project.
  e.g. git remote add <origin|upstream> git@github.com:<username>/<project-name>.git"
  ([username project-name base-dir]
   (git-add-remote username project-name base-dir "origin"))
  ([username project-name base-dir remote-label]
   (let [{:keys [exit out err]}
         (shell/sh "git" "remote" "add"
                   remote-label ;; "upstream" or "origin"
                   (str "git@github.com:" username "/" project-name ".git")
                   :dir base-dir)])))

(defn git-push-remote
  "Run git push send changes to remote repository.
  e.g. git push --set-upstream <origin|upstream> <master|branch-name>"
  ([]
   (git-push-remote "." "origin" "master"))
  ([base-dir]
   (git-push-remote base-dir "origin" "master"))
  ([base-dir remote-label]
   (git-push-remote base-dir remote-label "master"))
  ([base-dir remote-label branch-name]
   (fs/file base-dir)
   ;; e.g. git push --set-upstream <origin|upstream> branch-name
   (let [{:keys [exit out err]}
         (shell/sh "git" "push" "--set-upstream"
                   remote-label
                   branch-name
                   :dir base-dir)])))

(defn- expand-and-normalize
  "Allow the ~ to be expanded."
  [filename]
  (-> filename
      fs/expand-home
      fs/normalized))

;; https://docs.github.com/en/rest/reference/repos
;; https://docs.github.com/en/rest/reference/repos#create-a-repository-for-the-authenticated-user
(defn- default-options
  "Define the sensible default options when creating new Github repository"
  [options]
  (merge {:public false
          :has-issues false
          :has-wiki false
          :has-download false
          :delte-branch-on-merge true
          ;; :has-downloads true
          ;;:visibility "private" ;; or "public"
          ;;:archived false
          ;;:disabled false
          }
         options))

(defn- check-and-confirm-response
  "Check and confirm if we can successfully create a new Github repository."
  [response]
  (if (:status response)
    (println "Problem creating new repository, errors : " (get-in response [:body :errors]))
    (println (str "You have succesfully created new repository at : " (:html_url response)))))

(defn create-new-repo!
  "Create new repository using the given options"
  [options]
  (let [config-options (:config options)
        reponame (:repo options)]
    (try
      (when-let [config (read-config (expand-and-normalize config-options))]
        (let [username (:username config)
              password (:password config)
              auth (str username ":" password)
              github-prefix "https://github.com/"
              homepage (str github-prefix (str/join "/" (list username reponame)))
              response (repos/create-repo reponame (default-options {:auth auth
                                                                     :description (str reponame " by " username)
                                                                     :homepage homepage}))]
          (check-and-confirm-response response)

          ;; Make sure that we are running from the right directory
          (let [base-dir (fs/file ".")
                {:keys [init-commit
                        remote-label
                        push]} options]

            ;; Don't run git-init commit if project already containing .git directory
            (when-not (fs/exists? ".git")
              (when init-commit (git-init-commit base-dir)))

            ;; We always add remote tracking
            (git-add-remote username reponame base-dir remote-label)

            ;; Run git push only if the user specify '--push' option.
            (if (fs/exists? ".git")
              (when push (git-push-remote base-dir remote-label ""))
              ;; No valid git project, so let the the user know
              (println "FYI: As you don't have the valid git project, you may like to run git init && git push manually.")))))
      (opt/exit 0 "Done")
      (catch Exception e
        ;; Handle any problem/exception that we may have
        (opt/exit 1 (println (str "Error loading configuration file: " (.getMessage e))))))))

(defn -main [& args]
  (let [{:keys [options arguments errors summary]}
        (cli/parse-opts args opt/options)]
    (cond
      (:help options)
      (opt/exit 0 (opt/usage summary))

      (:config options)
      (if (:repo options)
        (create-new-repo! options)
        (opt/exit 0 (opt/usage summary)))

      ;; Printout the usage then exit
      :else (opt/exit 0 (opt/usage summary)))))
