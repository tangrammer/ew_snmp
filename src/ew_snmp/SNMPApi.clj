;; # GEN-CLASS to use from java environment once we have the standalone library
;;
;; ### Defaults:
;; **TransportMappingProtocol:** UDP
;;
;; **UDP-PORT get communication** 161
(ns ew_snmp.SNMPApi
  (:use [ew_snmp.v2 :only [ getv2]]
        [ew_snmp.v3 :only [ getv3]]
        )
  (:import [org.json JSONObject])


  (:gen-class :methods
              [#^{:static true} [v2 [String String String] String]
               #^{:static true} [v2Json [String String String] org.json.JSONObject]
               #^{:static true} [v2JsonInOut [org.json.JSONObject] org.json.JSONObject]
               #^{:static true} [v3 [String String String String] String]
               #^{:static true} [v2all [String String String] "[Ljava.lang.Object;"]
               #^{:static true} [v2allb [String String "[Ljava.lang.Object;"] "[Ljava.lang.Object;"]
               #^{:static true} [v3all [String String String String] "[Ljava.lang.Object;"]
               #^{:static true} [v3allb [String String String "[Ljava.lang.Object;"] "[Ljava.lang.Object;"]
               ]))

;(json/write-str {:a 1 :b 2})
;; # SNMP version 2
;; ---

(doto (JSONObject.) (.put "JSON" "hello world") (.toString))

(comment new JSONObject().put("JSON", "Hello, World!").toString())
(defn -v2
  "**v2**: usefull in case to have a single oid and want to return single string value
> [host, community-name, object-identifier] => String: first object"
  [h c oid]
  (first (getv2 h c [oid]))
  )




(defn -v2Json
  "**v2JsonInOut**: in case to have a JSONObject with host, community-name and oid single oid and want to return a json with response value
> [host, community-name, object-identifier] => org.json.JSONObject
"
  [h c oid]
  (doto (JSONObject.) (.put "response" (-v2 h c oid)))
  )


(defn -v2JsonInOut
  "**v2JsonInOut**: in case to have a JSONObject with host, community-name and oid single oid and want to return a json with response value
> [org.json.JSONObject {\"host\":\"\", \"community-name\":\"\", \"oid\":\"\"}] => org.json.JSONObject
"
  [in]
  (doto (JSONObject.) (.put "response" (-v2Json (.get in "host") (.get in "community-name") (.get in "oid"))))
  )

(defn -v2all
  "**v2all**: in case to have a single oid and want to return a array of string values
> [host, community-name, object-identifier] => [String]: all objects
"
  [h c oid]
  (to-array (getv2 h c [oid]))
  )

(defn -v2allb
  "**v2allb**: we pass an array of oids to obtain an array of values according to
> [host, community-name, array object-identifiers] => [String]: objects array"
  [h c oid]
  (to-array (getv2 h c oid))
  )

;; # SNMP version 3
;; ---

(defn -v3
  "**v3**: usefull in case to have a single oid and want to return single string value
> [host, community-name, object-identifier] => String: first object"
  [ip u p oid]
  (first (getv3 ip u p [oid])))

(defn -v3all
    "**v3all**: in case to have a single oid and want to return a array of string values
> [host, community-name, object-identifier] => [String]: all objects
"
  [ip u p oid]
  (to-array (getv3 ip u p [oid])))

 (defn -v3allb
   "**v3allb**: we pass an array of oids to obtain an array of values according to
> [host, community-name, array object-identifiers] => [String]: objects array"
  [ip u p oid]
  (to-array (getv3 ip u p oid)))


(comment
  (-v2 "localhost" "public" "1.3.6.1.2.1.1.5.0")
  (-v2all "localhost" "public" "1.3.6.1.2.1.1.5.0")
  (-v2allb "localhost" "public" ["1.3.6.1.2.1.1.5.0"])
  (-v3 "127.0.0.1" "juanv3" "comomolalagramola" "1.3.6.1.2.1.1.6.0")
  (-v3all "127.0.0.1" "juanv3" "comomolalagramola" "1.3.6.1.2.1.1.5.0")
  (-v3allb "127.0.0.1" "juanv3" "comomolalagramola" ["1.3.6.1.2.1.1.5.0" "1.3.6.1.2.1.1.6.0"] )
 )

;; # example of use from java
;;
;;     import ew_snmp.SNMPApi
;;     class HelloJava {
;;     public  static void  printArray(Object[] array)
;;     {
;;         for (int i = 0; i < array.length; i++)
;;             {
;;                 System.out.println("array:" + array[i]);
;;             }
;;     }
;;     public static void main(String[] args) {
;;         System.out.println("Hello from Java!");
;;         System.out.println("v2: "+SNMPApi.v2 ("localhost", "public", "1.3.6.1.2.1.1.5.0"));
;;         System.out.println("v2all:");
;;         HelloJava.printArray(SNMPApi.v2all ("localhost", "public", "1.3.6.1.2.1.1.5.0"));
;;         System.out.println("v2allb:");
;;         HelloJava.printArray(SNMPApi.v2allb ("localhost", "public", new String[] {"1.3.6.1.2.1.1.5.0", "1.3.6.1.2.1.1.6.0"}));
;;         System.out.println("v3: "+SNMPApi.v3 ("localhost", "juanv3", "comomolalagramola", "1.3.6.1.2.1.1.5.0"));
;;         System.out.println("v3all:");
;;         HelloJava.printArray(SNMPApi.v3all ("localhost","juanv3", "comomolalagramola", "1.3.6.1.2.1.1.5.0"));
;;         System.out.println("v3allb:");
;;         HelloJava.printArray(SNMPApi.v3allb ("localhost","juanv3", "comomolalagramola", new String[] {"1.3.6.1.2.1.1.5.0", "1.3.6.1.2.1.1.6.0"}));
;;         System.out.println("ERROR HOST: return java.lang.NullPointerException:  "+SNMPApi.v2 ("error-host", "public", "1.3.6.1.2.1.1.5.0"));
;;         System.out.println("ERROR community name return java.lang.NullPointerException:  "+SNMPApi.v2 ("localhost", "error-community", "1.3.6.1.2.1.1.5.0"));
;;     }
;; }
;;
