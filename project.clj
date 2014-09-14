(defproject shepherd "0.0.1"
  :description "A Clojure security library."
  :url "https://github.com/mischov/shepherd"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]

                 [shepherd/core "0.0.1"]
                 [shepherd/ring "0.0.1"]]
  :plugins [[lein-sub "0.2.4"]]
  :sub ["core" "ring"])
