(defproject xiana-experiment-flexiana "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :min-lein-version "2.0.0"
  :dependencies [[com.flexiana/framework "0.5.0-rc3"]
                 [piotr-yuxuan/closeable-map "0.36.2"]
                 [org.clojure/tools.namespace "1.3.0"]
                 [thheller/shadow-cljs "2.20.2"]
                 [com.fzakaria/slf4j-timbre "0.3.21"]
                 [com.flexiana/tiny-rbac "0.1.1"]
                 [bidi "2.1.6"]
                 [kibu/pushy "0.3.8"]
                 [buddy/buddy-sign "3.4.333"]
                 [com.flexiana/tiny-rbac "0.1.1"]
                 [cljs-ajax "0.8.4"]
                 [day8.re-frame/http-fx "0.2.3"]
                 [com.github.seancorfield/next.jdbc "1.3.858"]
                 ;[day8.re-frame/re-frame-10x "1.5.0"]
                 [reagent "0.10.0"]
                 [re-frame "1.1.2"]]
  :plugins [[lein-shadow "0.4.0"]
            [cider/cider-nrepl "0.25.9"]
            [lein-tailwind "0.1.2"]]
  :tailwind {:tailwind-dir "src/frontend/xiana_experiment_flexiana"
             :output-dir   "resources/public/assets"
             :tailwind-config  "tailwind.config.js"
             :styles [{:src "base.css"
                       :dst "main.css"}]}
  :hooks [leiningen.tailwind]
  :main ^:skip-aot xiana-experiment-flexiana.core
  :uberjar-name "xiana-experiment-flexiana.jar"
  :source-paths ["src/backend" "src/frontend" "src/shared"]
  :clean-targets ^{:protect false} ["resources/public/assets/js/compiled" "target"]
  :profiles {:dev   {:resource-paths ["dev" "config/dev"]
                     :dependencies   [[binaryage/devtools "1.0.3"]
                                      #_#_[day8.re-frame/tracing      "0.6.2"]
                                        [day8.re-frame/re-frame-10x "1.1.11"]]}
             :local {:resource-paths ["config/local"]}
             :prod  {:resource-paths ["config/prod"]}
             :test  {:resource-paths ["config/test"]
                     :dependencies   [[clj-http "3.12.3"]
                                      [clj-test-containers "0.7.2"]
                                      [kerodon "0.9.1"]]}}
  :shadow-cljs {:nrepl  {:port 8777}
                :builds {:app {:target     :browser
                               :output-dir "resources/public/assets/js/compiled"
                               :asset-path "assets/js/compiled"
                               :modules    {:app {:init-fn xiana-experiment-flexiana.core/init
                                                  :preloads [devtools.preload]}}}
                         #_#_:dev {:compiler-options
                                   {:output-feature-set :es2018
                                    :source-map         true
                                    :closure-defines    {frontend.interceptors.validate-schema? true
                                                         goog.DEBUG                             true
                                                         re-frame.trace.trace-enabled?          true
                                                         day8.re-frame.tracing.trace-enabled?   true
                                                         frontend.config/UPLOADS-URL-PREFIX     "/assets/attachments/"}}}
                         #_#_:dev-tools {:preloads [day8.re-frame-10x.preload
                                                    frontend.util.re-10x-preload]}
                         #_#_:debug {:dev-tools {:preloads [day8.re-frame-10x.preload]
                                                 :watch-dir "./resources/public/"}}}}
  :aliases {"migrate" ["run" "-m" "xiana.db.migrate"]
            "watch"   ["with-profile" "dev" "do"
                       ["shadow" "watch" "app" "browser-test" "karma-test"]]
            "release" ["with-profile" "prod" "do"
                       ["shadow" "release" "app"]]})
