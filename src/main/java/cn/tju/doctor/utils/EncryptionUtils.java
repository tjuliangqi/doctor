package cn.tju.doctor.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class EncryptionUtils {
    public static byte[] TripleDesEncrypt(byte[] content, byte[] key) throws Exception {
        byte[] icv = new byte[8];
        System.arraycopy(key, 0, icv, 0, 8);
        return TripleDesEncrypt(content, key, icv);
    }
    protected static byte[] TripleDesEncrypt(byte[] content, byte[] key, byte[] icv) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, "DESede");
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(icv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(content);
    }
    public static byte[] TripleDesDecrypt(byte[] content, byte[] key) throws Exception {
        byte[] icv = new byte[8];
        System.arraycopy(key, 0, icv, 0, 8);
        return TripleDesDecrypt(content, key, icv);
    }


    protected static byte[] TripleDesDecrypt(byte[] content, byte[] key, byte[] icv) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, "DESede");
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(icv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        return cipher.doFinal(content);
    }
    public static void main(String[] args) throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("order_id", "201609010016562012987");
        map.put("dealer_id", "2736123");
        map.put("broker_id", "yiyun");
        map.put("real_name", "张三");
        map.put("card_no", "6228880199872220");
        map.put("phone_no", "13488795491");
        map.put("id_card", "5326123123123211");
        map.put("pay", "100000.00");
        map.put("pay_remark", "");
        map.put("notify_url", "");
        map.put("key", "123456788765432112345678");

        String temp = "'{'\"order_id\": \"{0}\",\"dealer_id\": \"{1}\",\"broker_id\": \"{2}\",\"real_name\": \"{3}\",\"card_no\": \"{4}\",\"phone_no\": \"{5}\",\"id_card\": \"{6}\",\"pay\": \"{7}\"'}'";
        temp = MessageFormat.format(temp,map.get("order_id"),map.get("dealer_id"),map.get("broker_id"),map.get("real_name"),map.get("card_no"),map.get("phone_no"),map.get("id_card"),map.get("pay"));
        //byte[] content = "{\"order_id\": \"201609010016562012987\",\"dealer_id\": \"2736123\",\"broker_id\": \"yiyun\",\"real_name\": \"张三\",\"card_no\": \"6228880199872220\",\"phone_no\": \"13488795491\",\"id_card\": \"123532612312312321\",\"pay\": \"100000.00\"}".getBytes("utf-8");
        byte[] content = temp.getBytes("utf-8");
        byte[] key = "123456788765432112345678".getBytes("utf-8");
        byte[] enc = EncryptionUtils.TripleDesEncrypt(content, key);
        byte[] enc64 = Base64.encodeBase64(enc);
        System.out.println("encrypt: " + new String(enc64));
        byte[] dec64 = Base64.decodeBase64(enc64);
        byte[] dec = EncryptionUtils.TripleDesDecrypt(dec64, key);
        System.out.println("decrypt: " + new String(dec));
    }
}