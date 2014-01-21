(defproject com.enterpriseweb/snmp "0.0.1"
  :description "SNMP4J wrapper"
  :url "https://github.com/tangrammer/ew_snmp/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.snmp4j/snmp4j "2.2.3"]
                 [org.json/json "20131018"]
                 [org.clojure/data.json "0.2.4"]

]
  :repositories {"snmp4j" "https://oosnmp.net/dist/release"}
  :aot [com.enterpriseweb.snmp.V2 com.enterpriseweb.snmp.V3]
  :plugins [[lein-marginalia "0.7.1"]]
)
