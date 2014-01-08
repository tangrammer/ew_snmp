(ns ew_snmp.SNMPApi
  (:use [ew_snmp.v2 :only [ getv2]]
        [ew_snmp.v3 :only [ getv3]])
  (:gen-class :methods
              [#^{:static true} [v2 [String String String] String]
               #^{:static true} [v3 [String String String] String]]))


(defn -v2
  [h c oid]
  (first (getv2 h c oid))
  )


(defn -v3 [ip u p oid]
  (first (getv3 ip u p oid)))

(comment
  (-v2 "localost" "public" "1.3.6.1.2.1.1.5.0"))


(comment
  (-v3 "127.0.0.1" "juanv3" "comomolalagramola" "1.3.6.1.2.1.1.5.0"))
