package yzhpay.sdk.util;

import yzhpay.sdk.constant.ConfigPath;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtil {

    public static byte[] TripleDesEncrypt(byte[] content, byte[] key) throws Exception {
        byte[] icv = new byte[8];
        System.arraycopy(key, 0, icv, 0, 8);
        return TripleDesEncrypt(content, key, icv);
    }

    protected static byte[] TripleDesEncrypt(byte[] content, byte[] key, byte[] icv) throws Exception {
        final SecretKey secretKey = new SecretKeySpec(key, "DESede");
        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        final IvParameterSpec iv = new IvParameterSpec(icv);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        return cipher.doFinal(content);
    }

    public static byte[] TripleDesDecrypt(byte[] content, byte[] key) throws Exception {
        byte[] icv = new byte[8];
        System.arraycopy(key, 0, icv, 0, 8);
        return TripleDesDecrypt(content, key, icv);
    }

    protected static byte[] TripleDesDecrypt(byte[] content, byte[] key, byte[] icv) throws Exception {
        final SecretKey secretKey = new SecretKeySpec(key, "DESede");
        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        final IvParameterSpec iv = new IvParameterSpec(icv);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        return cipher.doFinal(content);
    }

    public static String encData(String data) throws Exception {
        byte[] content = data.getBytes("utf-8");
        byte[] key = Property.getProperties(ConfigPath.YZH_3DESKEY).getBytes("utf-8");

        byte[] enc = CipherUtil.TripleDesEncrypt(content, key);
        byte[] enc64 = Base64.encodeBase64(enc);
        return new String(enc64);
    }

    public static void main(String[] args) throws Exception {
        byte[] content = "{\"order_id\": \"201609010016562012987\",\"dealer_id\": \"2736123\",\"broker_id\": \"yiyun\",\"real_name\": \"张三\",\"card_no\": \"6228880199872220\",\"phone_no\": \"13488795491\",\"id_card\": \"123532612312312321\",\"pay\": \"100000.00\"}".getBytes("utf-8");
        byte[] key = "123456788765432112345678".getBytes("utf-8");

        byte[] enc = CipherUtil.TripleDesEncrypt(content, key);
        byte[] enc64 = Base64.encodeBase64(enc);
        System.out.println("encrypt: " + new String(enc64));

        byte[] dec64 = Base64.decodeBase64(enc64);
        byte[] dec = CipherUtil.TripleDesDecrypt(dec64, key);

        System.out.println("decrypt: " + new String(dec));
    }


}
