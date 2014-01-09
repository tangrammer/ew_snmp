(ns ew_snmp.SNMPApi
  (:use [ew_snmp.v2 :only [ getv2]]
        [ew_snmp.v3 :only [ getv3]])
  (:gen-class :methods
              [#^{:static true} [v2 [String String String] String]
               #^{:static true} [v3 [String String String String] String]
               #^{:static true} [v2all [String String String] "[Ljava.lang.Object;"]
               #^{:static true} [v2allb [String String "[Ljava.lang.Object;"] "[Ljava.lang.Object;"]
               #^{:static true} [v3all [String String String String] "[Ljava.lang.Object;"]
               #^{:static true} [v3allb [String String String "[Ljava.lang.Object;"] "[Ljava.lang.Object;"]
               ]))


(defn -v2
  [h c oid]
  (first (getv2 h c [oid]))
  )

(defn -v2all [h c oid]
  (to-array (getv2 h c [oid]))
  )

(defn -v2allb [h c oid]
  (to-array (getv2 h c oid))
  )


(defn -v3 [ip u p oid]
  (first (getv3 ip u p [oid])))

(defn -v3all [ip u p oid]
  (to-array (getv3 ip u p [oid])))

 (defn -v3allb [ip u p oid]
          (to-array (getv3 ip u p oid)))

(comment
  (-v2 "localhost" "public" "1.3.6.1.2.1.1.5.0")
  (-v2all "localhost" "public" "1.3.6.1.2.1.1.5.0")
  (-v2allb "localhost" "public" ["1.3.6.1.2.1.1.5.0"])
  (-v3 "127.0.0.1" "juanv3" "comomolalagramola" "1.3.6.1.2.1.1.6.0")
  (-v3all "127.0.0.1" "juanv3" "comomolalagramola" "1.3.6.1.2.1.1.5.0")
  (-v3allb "127.0.0.1" "juanv3" "comomolalagramola" ["1.3.6.1.2.1.1.5.0" "1.3.6.1.2.1.1.6.0"] )
 )
