; SPDX-FileCopyrightText: 2024 Imran M <imran.mustafa@ontariotechu.net
;
; SPDX-License-Identifier: GPL-3.0-or-later

(defproject chatty "1.0.0"
  :description "a simple chatbot based off of ELIZA"
  :url "https://github.com/the0xdev/chatty"
  :license {:name "GPL-3.0-or-later"
            :url "https://www.gnu.org/licenses/gpl-3.0.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :main ^:skip-aot chatty.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
