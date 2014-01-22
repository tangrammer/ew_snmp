# ew_snmp

A Clojure library to work with snmp4j.

It exposes 2 java classes to work from Java environment:
com.enterpriseweb.snmp.V2 and com.enterpriseweb.snmp.V3


## Java Test Example

```
package test;

import org.json.JSONObject;
import org.junit.Test;

import com.enterpriseweb.snmp.V2;
import com.enterpriseweb.snmp.V3;
import static org.junit.Assert.*;

public class SNMPCLJTest {
	@Test
	public void testPrintHelloWorld() {
		JSONObject jsonV2Object = createJSONV2Object("tcp", 261);
        System.out.println("V2 response: "+V2.json(jsonV2Object));
        //=> V2 response: {"response":["Right here, right now.","Vigor16"]}

        JSONObject jsonV3Object = createJSONV3Object("tcp", 261);
        System.out.println("V3 response: "+V3.json(jsonV3Object));
        //=> V3 response: {"response":["Right here, right now.","Vigor16"]}
        assertTrue(true);

	}

	private static JSONObject createJSONV2Object(String protocol, int port) {
		JSONObject jsonObject= new JSONObject();
        jsonObject.put("host", "localhost");
        jsonObject.put("community-name", "public");
        jsonObject.put("port", port);
        jsonObject.put("protocol", protocol);
        jsonObject.put("oids", new String[] {"1.3.6.1.2.1.1.6.0","1.3.6.1.2.1.1.5.0"});
		return jsonObject;
	}
	private static JSONObject createJSONV3Object(String protocol, int port) {
		JSONObject jsonObject= new JSONObject();
        jsonObject.put("host", "localhost");
        jsonObject.put("user-name", "juanv3");
        jsonObject.put("user-pass", "comomolalagramola");
        jsonObject.put("user-phrase", "comomolalagramola");
        jsonObject.put("port", port);
        jsonObject.put("protocol", protocol);
        jsonObject.put("oids", new String[] {"1.3.6.1.2.1.1.6.0","1.3.6.1.2.1.1.5.0"});
		return jsonObject;
	}

}

```

# generating and using java library
(requirements: Maven and Leiningen)

**Generating java library and installing in local maven repo**

Pay attention on version library (written on project.clj file)
```
$ cd ew_snmp
$ lein clean
$ lein install
```

**Testing library from java code (dependencies defined in pom.xml, JUnit tests included in scr/test/java source folder)**

Prior to test library you have to start SNMP daemon with protocol on osx:
https://github.com/tangrammer/ew_snmp/wiki/Enabling-snmp-protocols

And then:...

```
$ cd ew_snmp
$ mvn clean
$ mvn test

```

# Resources and documentation used on development
https://github.com/tangrammer/ew_snmp/wiki/Resources-used-on-prototype-development
