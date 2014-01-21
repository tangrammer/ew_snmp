(ns com.enterpriseweb.snmp.V2
  (:use [ew_snmp.v2 :only (v2-json)]
        [com.enterpriseweb.snmp.utils])
  (:require [clojure.data.json :as clj-json])
  (:import [org.json JSONObject])
  (:gen-class :methods
              [#^{:static true} [json [org.json.JSONObject] org.json.JSONObject]])
  )


(defn create-v2-call [ protocol port]
  {:protocol (name protocol)
   :port port
   :community-name "public"
   :host "localhost"
   :oids  ["1.3.6.1.2.1.1.6.0" "1.3.6.1.2.1.1.5.0"]})

(defn create-default-v2-json-object []
  (clj-json/write-str  (create-v2-call :tcp 261)))



; using a clojure json object
(comment "testing: "
         (call-api-version-json v2-json (create-json-java-object (create-default-v2-json-object))))


(defn create-default-v2-json-java-object []
  (doto (JSONObject.)
    (.put "protocol" "tcp")
    (.put "port" 261)
    (.put "community-name" "public")
    (.put "host" "localhost")
    (.put "oids" ["1.3.6.1.2.1.1.6.0" "1.3.6.1.2.1.1.5.0"])))


(comment "testing"
         (call-api-version-json v2-json (create-default-v2-json-java-object)))


(defn -json [json-java-object]
  (let [response (call-api-version-json v2-json json-java-object)]
    (create-json-java-object (clj-json/write-str {:response response}))
    )
  )

(comment
  "testing:"
  (-json (create-json-java-object (create-default-v2-json-object))))
