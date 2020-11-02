package config;

public class Test {
    public static void main(String[] args) {

        String aa = SysConfig.getProperty("aep.appkey");

        System.out.println(aa);

    }
}
