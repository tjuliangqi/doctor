package yzhpay.sdk.util;

import java.util.Random;

public class OrderUtil {

    private static Random random = new Random(1);

    public static String getMess() {
        return random.nextInt(10000000) + "";
    }

    public static String getOrderId(String prefix) {
        return prefix + System.currentTimeMillis() + random.nextInt(1000);
    }


}
