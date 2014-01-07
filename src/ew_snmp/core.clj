(ns ew-snmp.core

  (:import [java.io IOException]
           [org.snmp4j TransportMapping Snmp PDU CommunityTarget]
           [org.snmp4j.mp SnmpConstants]
           [org.snmp4j.transport DefaultUdpTransportMapping]
           [org.snmp4j.event ResponseEvent]
           [org.snmp4j.smi OID OctetString VariableBinding UdpAddress GenericAddress]
           )
  )
(def address "udp:127.0.0.1/161")

(declare snmp)

(defn get-target []

  (let [target-address (GenericAddress/parse address)
        target (CommunityTarget.)]
    (.setCommunity target (OctetString. "public"))
    (.setAddress target target-address)
    (.setRetries target 2)
    (.setTimeout target 1500)
    (.setVersion target (SnmpConstants/version2c))
    target
    )
  )

(defn get [oids]
  (let [pdu (PDU.)]
    (doall (map #(.add pdu (VariableBinding. %)) oids))
    (.setType pdu PDU/GET)
    (if-let [send-event (.send snmp pdu (get-target) nil)]
      send-event
      (do
        (println "RUNTIME EXCEPTION")
        (throw (RuntimeException. "GET timed out!")))
      )
    )
  )

(defn get-as-string [oid]
  #_(let [oid-p ([oid])
        event (get oid-p)]
    (.. event (.getResponse) (.get 0) (.getVariable) (.toString))
    )
  )

(defn start []

  (let [ transport (DefaultUdpTransportMapping.)
        -snmp (Snmp. transport)]
    (def snmp -snmp)
    (.listen transport)
    )


  )
