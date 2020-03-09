package cn.tju.doctor.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class EncryptionUtils {

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    public  static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }
    /**
     * sha256_HMAC加密
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     */
    public static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    public static String[] encryptAndSign(Map<String,String> map1, Map<String, String> map2) throws Exception{
        //String temp = "'{'\"order_id\": \"{0}\",\"dealer_id\": \"{1}\",\"broker_id\": \"{2}\",\"real_name\": \"{3}\",\"card_no\": \"{4}\",\"phone_no\": \"{5}\",\"id_card\": \"{6}\",\"pay\": \"{7}\"'}'";
       // temp = MessageFormat.format(temp,map1.get("order_id"),map1.get("dealer_id"),map1.get("broker_id"),map1.get("real_name"),map1.get("card_no"),map1.get("phone_no"),map1.get("id_card"),map1.get("pay"));
        byte[] content = "{\"order_id\": \"201609010016562012987\",\"dealer_id\":\"2736123\",\"broker_id\": \"yiyun\",\"real_name\": \"张三\",\"card_no\":\"6228880199872220\",\"phone_no\": \"13488795491\",\"id_card\":\"123532612312312321\",\"pay\": \"100000.00\"}".getBytes("utf-8");
        byte[] key = "123456788765432112345678".getBytes("utf-8");
        //byte[] content = temp.getBytes("utf-8");
        //byte[] key = map1.get("3des_key").getBytes("utf-8");
        byte[] enc = secUtils.TripleDesEncrypt(content, key);
        byte[] enc64 = Base64.encodeBase64(enc);


        String data = new String(enc64);
        System.out.println("encrypt: " + data);
        String needSignString = "data={0}&mess={1}&timestamp={2}&key={3}";
        needSignString = MessageFormat.format(needSignString,data, map2.get("mess"), map2.get("timestamp"), map2.get("appkey"));
        String readySignString = sha256_HMAC(needSignString, map2.get("appkey"));
        System.out.println("sign: " + readySignString);
        String[] result = new String[]{data, readySignString};
        return result;
    }

    public static void main(String[] args) throws Exception {
        Map<String,String> map1 = new HashMap<>();
        map1.put("order_id", "201609010016562012987");
        map1.put("dealer_id", "2736123");
        map1.put("broker_id", "yiyun");
        map1.put("real_name", "张三");
        map1.put("card_no", "6228880199872220");
        map1.put("phone_no", "13488795491");
        map1.put("id_card", "5326123123123211");
        map1.put("pay", "100000.00");
        map1.put("pay_remark", "");
        map1.put("notify_url", "");
        map1.put("3des_key", "123456788765432112345678");
        Map<String,String> map2 = new HashMap<>();
        map2.put("mess", "12313");
        map2.put("timestamp", "123457");
        map2.put("appkey", "78f9b4fad3481fbce1df0b30eee58577");
        String[] result = encryptAndSign(map1, map2);
        System.out.println(result[0]+"\n"+ result[1]);
    }
}