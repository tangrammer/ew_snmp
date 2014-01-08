import ew_snmp.SNMPApi;


class HelloJava {
    public static void main(String[] args) {
        System.out.println("Hello from Java!");
        System.out.println(SNMPApi.v2 ("localhost", "public", "1.3.6.1.2.1.1.5.0"));
        System.out.println(SNMPApi.v3 ("juanv3", "comomolalagramola", "1.3.6.1.2.1.1.5.0"));
    }
}
