# ew_snmp

A Clojure library to work with snmp4j.
So far based in https://github.com/maoe/snmp4clj and https://github.com/jlbfalcao/clojure-snmp

# generating and using clojure jar
```
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
```

The HelloJava.java class
```
import ew_snmp.SNMPApi;


class HelloJava {
    public static void main(String[] args) {
        System.out.println("Hello from Java!");
        System.out.println(SNMPApi.v2 ("localhost", "public", "1.3.6.1.2.1.1.5.0"));
        System.out.println(SNMPApi.v3 ("localhost", "juanv3", "comomolalagramola", "1.3.6.1.2.1.1.5.0"));
    }
}

```



# Resources used on development
https://github.com/tangrammer/ew_snmp/wiki/Resources-used-on-prototype-development

# clojure doc api
http://tangrammer.github.com/ew_snmp/

# java doc api
https://github.com/tangrammer/ew_snmp/wiki/Java-API
