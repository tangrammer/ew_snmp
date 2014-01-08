import ew_snmp.falcao;
import ew_snmp.v3;


class HelloJava {
    public static void main(String[] args) {
        System.out.println("Hello from Java!");
        System.out.println(falcao.v1 ("localhost", "public", "1.3.6.1.2.1.1.5.0"));
        System.out.println(v3.v3 ("juanv3", "comomolalagramola", "1.3.6.1.2.1.1.5.0"));
    }
}
