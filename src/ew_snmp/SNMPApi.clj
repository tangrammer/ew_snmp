(ns ew_snmp.SNMPApi
  (:use [ew_snmp.falcao :only [ snmpgetv2]]
        [ew_snmp.v3 :only [ getv3]])
  (:gen-class :methods
              [#^{:static true} [v2 [String String String] String] #^{:static true} [v3 [String String String] String]]
              ))


(defn -v2 [h c oid]
  (first (snmpgetv2 h c oid))
  )
(-v2 "localhost" "public" "1.3.6.1.2.1.1.5.0")


(defn -v3 [u p oid]
  (first (getv3 u p oid)))
(-v3 "juanv3" "comomolalagramola" "1.3.6.1.2.1.1.5.0")
