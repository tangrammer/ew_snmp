# ew_snmp

A Clojure library to work with snmp4j.
So far based in https://github.com/maoe/snmp4clj and https://github.com/jlbfalcao/clojure-snmp

# generating and using clojure jar
$ cd ew_snmp
$ lein compile
$ lein uberjar
$ cd HelloJava
;;; edit what you need into HelloJava/HelloJava.class
$ cp ../target/ew_snmp-0.1.0-standalone.jar .
$ javac -cp '.:ew_snmp-0.1.0-standalone.jar' HelloJava.java
$ java -cp '.:ew_snmp-0.1.0-standalone.jar' HelloJava
>> Hello from Java!
>> Vigor21
>> Vigor21


## License

Copyright © 2014

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
