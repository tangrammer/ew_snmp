(ns ew_snmp.v3
  (:use [ew_snmp.falcao :only (response? )])
  (:import
   [org.snmp4j ScopedPDU Snmp PDU CommunityTarget UserTarget]
   [org.snmp4j.security USM SecurityProtocols AuthMD5 SecurityModels UsmUser SecurityLevel PrivDES]
   [org.snmp4j.mp SnmpConstants MPv3]
   [org.snmp4j.transport DefaultUdpTransportMapping]
   [org.snmp4j.event.ResponseEvent]
   [org.snmp4j.smi OID VariableBinding OctetString GenericAddress UdpAddress IpAddress]
   [java.net InetAddress]
   )
  (:gen-class :methods [#^{:static true} [v3 [String String String] String]])
  )

(def ip-address "localhost")
(def port "161")

(defn build-pdu [oid]
  "Build PDU Object"
  (doto (ScopedPDU.)
    (.setType PDU/GETBULK)
    (.addAll (into-array (map #(VariableBinding. (OID. (str %))) oid)))
    )
  )


(defn getv3 [user-name user-pass & oid]
  (let [pdu (build-pdu oid)
        usm (USM. (SecurityProtocols/getInstance) (OctetString. (MPv3/createLocalEngineID) ) 0)]
    (.addSecurityModel (SecurityModels/getInstance) usm)
    (let [snmp (Snmp. (DefaultUdpTransportMapping.))
          user-name-octet (OctetString. user-name)
          user-target (UserTarget.)
          address (UdpAddress. (str  ip-address "/" port))
          usm-user (UsmUser. user-name-octet  AuthMD5/ID (OctetString. user-pass) PrivDES/ID (OctetString. user-pass))
          ]
      (.addUser (.getUSM snmp) user-name-octet usm-user)

                                        ;todo


      (.setAddress user-target address)
      (.setVersion user-target SnmpConstants/version3)
      (.setRetries user-target 0)
      (.setTimeout user-target 1000)
      (.setSecurityLevel user-target SecurityLevel/AUTH_PRIV)
      (.setSecurityName user-target user-name-octet)
      (.listen snmp)
      (let [event (. snmp get pdu user-target)
            response (. event getResponse)]
        (. snmp close)
        (if (response? response)
          (vec (map #(do
                       (let [xv (. % getVariable)]
                         #_(println % xv)
                         (str xv))) (. response getVariableBindings)))
          (println "no response" event)
          )))))
(defn -v3 [u p oid]
  (first (getv3 u p oid)))
(-v3 "juanv3" "comomolalagramola" "1.3.6.1.2.1.1.2.0")
