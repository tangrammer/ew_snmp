(ns com.enterpriseweb.snmp.utils
    (:require [clojure.data.json :as clj-json])
  (:import [org.json JSONObject])
)

(defn create-json-java-object [clojure-json-object]
  (JSONObject. (str clojure-json-object)))


(defn call-api-version-json [version-fn java-json-object]
  (version-fn (clj-json/read-str (.toString java-json-object) :key-fn keyword)))
