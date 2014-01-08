;; ## this ns try to encapsulate all the knowledge releated with NSMP v2c
;; so far only we worried about "get" actions
;; maybe differents types of community-target, pdus and GET options (GET, GETNEXT, GETBULK)
(ns ew_snmp.v2
  (:import
    [org.snmp4j Snmp PDU CommunityTarget]
    [org.snmp4j.mp SnmpConstants]
    [org.snmp4j.transport DefaultUdpTransportMapping]
    [org.snmp4j.event.ResponseEvent]
    [org.snmp4j.smi OID VariableBinding OctetString GenericAddress]
    [java.net InetAddress]
    )
  )

(defn build-target
  "Returns CommunityTarget object"
  [host community]

  (doto (CommunityTarget.)
    (.setCommunity (new OctetString community))
    (.setAddress (GenericAddress/parse (format "udp:%s/161" host)))
    (.setVersion SnmpConstants/version2c)
    (.setRetries 0)
    (.setTimeout 1000)
    )
  )

(comment
  (println (str "@" (build-target "host" "public"))))

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
    (println t)
   (and response (== (. response getType) PDU/RESPONSE)))
  )
(comment
  (response? nil))

;; **core function**
;;
;;    this function retuns an array [] of return values or exceptions cached
;;
;;    `(= ["Right here, right now."] (getv2 "localhost" "public" "1.3.6.1.2.1.1.6.0"))`
;;
;;    `(= ["java.lang.NullPointerException"] (getv2 "error" "public" "1"))`
;;
;;    `(= ["java.lang.NullPointerException"] (getv2 "localhost" "error" "1"))`
;;
(defn getv2 [host community & oid]
  (try

    (do
      (def pdu (build-pdu oid))
      (def target (build-target host community))
      (def snmp (Snmp. (DefaultUdpTransportMapping.)))
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
    (catch NullPointerException e [(str  e)]))
  )

(comment
  (= ["Right here, right now."] (getv2 "localhost" "public" "1.3.6.1.2.1.1.6.0"))
  (= ["java.lang.NullPointerException"] (getv2 "error" "public" "1"))
  (= ["java.lang.NullPointerException"] (getv2 "localhost" "error" "1"))
  (vec
   (map
    #(getv2 "localhost" "public" % ) ["1.3.6.1.2.1.1.5.0" "1.3.6.1.2.1.1.6.0" ".1.3.6.1.2.1.25.3.6.1.4" "1.3.6.1.2.1.31.1.1.1.1.0" "1.3.6.1.2.1.1.3.0"])))
