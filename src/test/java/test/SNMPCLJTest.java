package test;
import java.lang.reflect.Method;

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
