(ns ew_snmp.v3
  (:use [ew_snmp.falcao :only (response? build-pdu)])
  (:import
   [org.snmp4j ScopedPDU Snmp PDU CommunityTarget UserTarget]
   [org.snmp4j.security USM SecurityProtocols AuthMD5 SecurityModels UsmUser SecurityLevel]
   [org.snmp4j.mp SnmpConstants MPv3]
   [org.snmp4j.transport DefaultUdpTransportMapping]
   [org.snmp4j.event.ResponseEvent]
   [org.snmp4j.smi OID VariableBinding OctetString GenericAddress UdpAddress IpAddress]
   [java.net InetAddress]
   )
  )

(def ip-address "localhost")
(def port "161")




(defn getv3 [user-name user-pass]
  (let [pdu (ScopedPDU.)
        usm (USM. (SecurityProtocols/getInstance) (OctetString. (MPv3/createLocalEngineID) ) 0)]
    (.addSecurityModel (SecurityModels/getInstance) usm)
    (let [snmp (Snmp. (DefaultUdpTransportMapping.))
          user-name-octet (OctetString. user-name)
          user-target (UserTarget.)
          address (UdpAddress. (str  ip-address "/" port))
          ]
      (.addUser (.getUSM snmp) user-name-octet (UsmUser. user-name-octet  AuthMD5/ID (OctetString. user-pass) AuthMD5/ID nil))

      ;todo


      (.setAddress user-target address)
      (.setVersion user-target SnmpConstants/version3)
      (.setRetries user-target 0)
      (.setTimeout user-target 1000)
      (.setSecurityLevel user-target SecurityLevel/AUTH_PRIV)
      (.setSecurityName user-target user-name-octet)
      (.listen snmp)

      )

    )

  )

(getv3 "juanv3" "comomolagramola")
