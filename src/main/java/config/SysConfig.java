package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 读取公共的配置文件
 */

public class SysConfig {

    private static Properties sysConfig = new Properties();


    static { //读取配置文件

        InputStream inputStream = SysConfig.class

                .getResourceAsStream(ConfigConstants.AEPCONFIG_PATH);

        try {

            sysConfig.load(inputStream);

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                inputStream.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }


//根据属性读取配置文件

    public static String getProperty(String key) {

        return sysConfig.getProperty(key);

    }


//根据属性写入配置文件

    public static void setProperty(String key, String value) {

        sysConfig.setProperty(key, value);

    }


}