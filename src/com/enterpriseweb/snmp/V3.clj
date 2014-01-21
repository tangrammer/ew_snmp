(ns com.enterpriseweb.snmp.V3
  (:use [ew_snmp.v3 :only (v3-json)]
        [com.enterpriseweb.snmp.utils])
  (:require [clojure.data.json :as clj-json])
  (:import [org.json JSONObject])
  (:gen-class :methods
              [#^{:static true} [json [org.json.JSONObject] org.json.JSONObject]])
  )


(defn create-v3-call [protocol port]
  {:protocol (name protocol)
   :port port
   :host "localhost"
   :user-name "juanv3"
   :user-pass "comomolalagramola"
   :user-phrase "comomolalagramola"
   :oids  ["1.3.6.1.2.1.1.6.0" "1.3.6.1.2.1.1.5.0"]})

(defn create-default-v3-json-object []
  (clj-json/write-str  (create-v3-call :tcp 261)))


; using a clojure json object
(comment "testing: "
         (call-api-version-json v3-json (create-json-java-object (create-default-v3-json-object))))


(defn create-default-v3-json-java-object []
  (doto (JSONObject.)
    (.put "protocol" "tcp")
    (.put "port" 261)
    (.put "host" "localhost")
    (.put "user-name" "juanv3")
    (.put "user-pass" "comomolalagramola")
    (.put "user-phrase" "comomolalagramola")
    (.put "oids" ["1.3.6.1.2.1.1.6.0" "1.3.6.1.2.1.1.5.0"])))


(comment "testing"
         (call-api-version-json v3-json (create-default-v3-json-java-object)))


(defn -json [json-java-object]
  (let [response (call-api-version-json v3-json json-java-object)]
    (create-json-java-object (clj-json/write-str {:response response}))
    )
  )

(comment
  "testing:"
  (-json (create-json-java-object (create-default-v3-json-object))))
