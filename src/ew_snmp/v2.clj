;; ## this ns try to encapsulate all the knowledge releated with NSMP v2c
;; so far only we worried about "get" actions
;; maybe differents types of community-target, pdus and GET options (GET, GETNEXT, GETBULK)
(ns ew_snmp.v2
    (:require [clojure.data.json :as json])
  (:import
    [org.snmp4j Snmp PDU CommunityTarget]
    [org.snmp4j.mp SnmpConstants]
    [org.snmp4j.transport DefaultUdpTransportMapping DefaultTcpTransportMapping]
    [org.snmp4j.event.ResponseEvent]
    [org.snmp4j.smi OID VariableBinding OctetString GenericAddress]
    [java.net InetAddress]
    )
  )



;; A CommunityTarget represents SNMP target properties
;; for community based message processing models (SNMPv1 and SNMPv2c).
;; extends AbstractTarget functionality::
;; clone, getAddress, getMaxSizeRequestPDU, getPreferredTransports,
;; getRetries, getSecurityLevel, getSecurityName, getTimeout, getVersion,
;; setAddress, setMaxSizeRequestPDU, setPreferredTransports, setRetries,
;; setSecurityName, setTimeout, setVersion, toStringAbstractTarget
(defn build-target
  "Returns CommunityTarget object, it has only 3 fiels to be configurable
   name, security-model and security-level"
  [[protocol host port] community]

  (doto (CommunityTarget.)
    (.setCommunity (OctetString. community))
    (.setAddress (GenericAddress/parse (str protocol":" host "/" port)))
    (.setVersion SnmpConstants/version2c)
    (.setRetries 0)
    (.setTimeout 1000)
    )
  )

(comment
  (println (str "@" (build-target ["udp" "host" 161] "public")))

  ;; The PDU class represents a SNMP protocol data unit.
  ;; The PDU version supported by the BER decoding and encoding methods of this class is v2.
  ;; The default PDU type is GET.
  )

(defn build-pdu
  "Build PDU Object"
  [oid]
  (doto (PDU.)
    (.setType PDU/GET)
    (.addAll (into-array (map #(VariableBinding. (OID. (str %))) oid)))
    )
  )

(comment
  (build-pdu '("1.3.6.1.2.1.1.3.0" "1.3.6.1.2.1.2.2.1.21.1")))


;; checking response and by the way printing the response type on console
(defn response? [response]
  (let [t (. response getType)]
    #_(println t)
   (and response (== (. response getType) PDU/RESPONSE)))
  )
(comment
  (response? nil))

(defn build-snmp
  "The Snmp class is transport protocol independent.
   Available TransportMapping implementations :
   AbstractTransportMapping, DefaultSshTransportMapping,
   DefaultTcpTransportMapping, DefaultUdpTransportMapping,
   DummyTransport, DummyTransport.DummyTransportResponder,
   TcpTransportMapping, TLSTM, UdpTransportMapping.

   Transport mappings are used for incoming and outgoing messages."
  [type]
  (condp = type
    "udp" (Snmp. (DefaultUdpTransportMapping.))
    "tcp" (Snmp. (DefaultTcpTransportMapping.))
    )
  )



(defn getv2 [[protocol host port] community & oid]
  (try

    (do
      (def pdu (apply build-pdu oid))
      (def target (build-target [protocol host port] community))
      (def snmp (build-snmp protocol))
      (. snmp listen)

      (def event (. snmp send pdu target nil))
      (def response (. event getResponse))
      (. snmp close)

      (when (response? response)
                                        ; return data.
        (vec (map #(do
                     (let [xv (. % getVariable)]
                       #_(println % xv)
                       (str xv))) (. response getVariableBindings)))
        )
      )
    (catch NullPointerException e
      (do
        ;(println response)
              [(str  e)])))
  )



(defn v2-json
  "j is a data json-object"
  [{:keys [protocol host port community-name oids ] :as j}]
  (getv2 [protocol host port] community-name oids)
  )





;; **core function**
;;
;;    this getv2 fn retuns an array [] of return values or exceptions cached
;;
;;    `(= ["Right here, right now."] (getv2 "localhost" "public" "1.3.6.1.2.1.1.6.0"))`
;;
;;    `(= ["java.lang.NullPointerException"] (getv2 "error" "public" "1"))`
;;
;;    `(= ["java.lang.NullPointerException"] (getv2 "localhost" "error" "1"))`
;;

(comment

  (v2-json {:protocol "tcp" :community-name "public" :host "localhost" :port 261 :oids ["1.3.6.1.2.1.1.6.0"]})

  ; tcp example
  (= ["Right here, right now."] (getv2 ["tcp" "localhost" 261] "public" ["1.3.6.1.2.1.1.6.0"]))
  ; udp example
  (= ["Right here, right now."] (getv2 ["udp" "localhost" 161] "public" ["1.3.6.1.2.1.1.6.0"]))

  (= ["java.lang.NullPointerException"] (getv2  ["udp" "error" 161] "public" ["1"]))
  (= ["java.lang.NullPointerException"] (getv2 ["udp" "localhost" 161] "error" ["1"]))
  (vec
   (map
    #(getv2 ["tcp" "localhost" 261]  "public" [%] ) ["1.3.6.1.2.1.1.5.0" "1.3.6.1.2.1.1.6.0" ".1.3.6.1.2.1.25.3.6.1.4" "1.3.6.1.2.1.31.1.1.1.1.0" "1.3.6.1.2.1.1.3.0"])))
