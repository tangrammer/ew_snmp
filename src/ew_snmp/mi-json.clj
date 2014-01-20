(ns ew_snmp.mi-jason
  (:use [ew_snmp.v2])
  (:require [clojure.data.json :as json]
            )
  (:import [org.json JSONObject]))


(defn create-default-udp-json-object []
  (json/write-str  {:protocol "tcp"
                    :port 261
                    :version 2
                    :community-name "public"
                    :host "localhost"
                    :oids  ["1.3.6.1.2.1.1.6.0" "1.3.6.1.2.1.1.5.0"]})

  )
 (v2-json (json/read-str (.toString (JSONObject. (str (create-default-udp-json-object)))) :key-fn keyword))
(defn create-default-udp-json-java-object []
  (doto (JSONObject.)
    (.put "JSON" "hello world")
    (.put "version" "2")
    (.put "protocol" "udp")
    (.put "port" 161)
    (.put "community-name" "public")
    (.put "host" "localhost")
    )
  )


(create-default-udp-json-object)


(let [json-java-object (create-default-udp-json-java-object)
      json-clojure-object (json/read-str  (str json-java-object)   :key-fn keyword)]
  (:JSON json-clojure-object)
  (json/write-str json-clojure-object :key-fn #(.toUpperCase (.toString   (name %))))

  )
