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
        System.out.println("v3: "+SNMPApi.v3 ("localhost", "juanv3", "comomolalagramola", "1.3.6.1.2.1.1.5.0"));
        System.out.println("v3all:");
        HelloJava.printArray(SNMPApi.v3all ("localhost","juanv3", "comomolalagramola", "1.3.6.1.2.1.1.5.0"));
    }
}
