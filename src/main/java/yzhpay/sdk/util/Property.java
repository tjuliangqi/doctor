package yzhpay.sdk.util;

import yzhpay.sdk.constant.ConfigPath;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Property {
    private static Properties properties = new Properties();

    static {
        try {
            InputStream in = new BufferedInputStream(new FileInputStream("./src/main/resources/diff.properties"));
            properties.load(in);
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getProperties(String key) {
        return StringUtils.trim(properties.getProperty(key));
    }

    public static String getUrl(String url) {
        return StringUtils.trim(properties.getProperty(ConfigPath.YZH_URL))
                + StringUtils.trim(properties.getProperty(url));
    }

}
