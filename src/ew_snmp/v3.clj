(ns ew_snmp.v3
  (:use [ew_snmp.v2 :only (response? build-snmp)])
  (:import
   [org.snmp4j ScopedPDU Snmp PDU CommunityTarget UserTarget]
   [org.snmp4j.security USM SecurityProtocols AuthMD5 SecurityModels UsmUser SecurityLevel PrivDES]
   [org.snmp4j.mp SnmpConstants MPv3]
   [org.snmp4j.transport DefaultUdpTransportMapping]
   [org.snmp4j.event.ResponseEvent]
   [org.snmp4j.smi OID VariableBinding OctetString GenericAddress UdpAddress IpAddress TcpAddress]
   [java.net InetAddress]
   )
  (:gen-class :methods [#^{:static true} [v3 [String String String] String]])
  )



;; The ScopedPDU class represents a SNMPv3 scoped PDU.
(defn build-pdu
  "Build ScopedPDU Object"
  [oid]
  (doto (ScopedPDU.)
    (.setType PDU/GETBULK)
    (.addAll (into-array (map #(VariableBinding. (OID. (str %))) oid)))
    )
  )

(defn build-address
  "The IpAddress class represents an IPv4 address SNMP variable."
  [protocol ip-address port]
  (condp = protocol
    "udp" (UdpAddress. (str  ip-address "/" port))
    "tcp" (TcpAddress. (str  ip-address "/" port))
    )
  )

(defn getv3 [[protocol ip-address port] user-name user-pass user-phrase & oid]
  (let [pdu (apply build-pdu oid)
        usm (USM. (SecurityProtocols/getInstance) (OctetString. (MPv3/createLocalEngineID) ) 0)]
    (.addSecurityModel (SecurityModels/getInstance) usm)
    (let [snmp (build-snmp protocol)
          user-name-octet (OctetString. user-name)
          user-target (UserTarget.)
          address (build-address protocol ip-address port)
          usm-user (UsmUser. user-name-octet  AuthMD5/ID (OctetString. user-pass) PrivDES/ID (OctetString. user-phrase))
          ]
      (.addUser (.getUSM snmp) user-name-octet usm-user)
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


(getv3 ["tcp" "localhost" 261] "juanv3" "comomolalagramola" "comomolalagramola" ["1.3.6.1.2.1.1.6.0"])


(defn v3-json
  "j is a data json-object"
  [{:keys [protocol host port user-name user-pass user-phrase oids ] :as j}]
  (getv3 [protocol host port] user-name user-pass user-phrase oids)
  )
