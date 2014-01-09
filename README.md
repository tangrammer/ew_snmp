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
    public  static void  printArray(Object[] array)
    {
        for (int i = 0; i < array.length; i++)
            {
                System.out.println("array:" + array[i]);
            }


    }


    public static void main(String[] args) {
        System.out.println("Hello from Java!");

        System.out.println("v2: "+SNMPApi.v2 ("localhost", "public", "1.3.6.1.2.1.1.5.0"));
        System.out.println("v2all:");
        HelloJava.printArray(SNMPApi.v2all ("localhost", "public", "1.3.6.1.2.1.1.5.0"));
        System.out.println("v2allb:");
        HelloJava.printArray(SNMPApi.v2allb ("localhost", "public", new String[] {"1.3.6.1.2.1.1.5.0", "1.3.6.1.2.1.1.6.0"}));


        System.out.println("v3: "+SNMPApi.v3 ("localhost", "juanv3", "comomolalagramola", "1.3.6.1.2.1.1.5.0"));
        System.out.println("v3all:");
        HelloJava.printArray(SNMPApi.v3all ("localhost","juanv3", "comomolalagramola", "1.3.6.1.2.1.1.5.0"));

        System.out.println("v3allb:");
        HelloJava.printArray(SNMPApi.v3allb ("localhost","juanv3", "comomolalagramola", new String[] {"1.3.6.1.2.1.1.5.0", "1.3.6.1.2.1.1.6.0"}));


        System.out.println("ERROR HOST: return java.lang.NullPointerException:  "+SNMPApi.v2 ("error-host", "public", "1.3.6.1.2.1.1.5.0"));

        System.out.println("ERROR community name return java.lang.NullPointerException:  "+SNMPApi.v2 ("localhost", "error-community", "1.3.6.1.2.1.1.5.0"));
    }
}


```



# Resources used on development
https://github.com/tangrammer/ew_snmp/wiki/Resources-used-on-prototype-development

# clojure doc api
`lein marg -f index.html`
http://tangrammer.github.com/ew_snmp/

# java doc api
https://github.com/tangrammer/ew_snmp/wiki/Java-API
