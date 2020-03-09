package cn.tju.doctor.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class PostUtils {

    public static String post(Map<String, String> headerMap, Map<String, String> map1, Map<String, String> map2, String url) throws Exception{

        String[] dataAndSign = EncryptionUtils.encryptAndSign(map1, map2);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", dataAndSign[0]);
        jsonObject.put("mess", map2.get("mess"));
        jsonObject.put("timestamp", map2.get("timestamp"));
        jsonObject.put("sign", dataAndSign[1]);
        jsonObject.put("sign_type","sha256");

        String result = "";
        HttpPost post = new HttpPost(url);
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();

            //post.setHeader("Content-Type","application/json;charset=utf-8");
            //post.addHeader("Authorization", "Basic YWRtaW46");
            post.setHeader("dealer-id", headerMap.get("dealer_id"));
            post.addHeader("request-id", headerMap.get("request_id"));
            StringEntity postingString = new StringEntity(jsonObject.toString(),"utf-8");
            post.setEntity(postingString);
            HttpResponse response = httpClient.execute(post);

            InputStream in = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
            StringBuilder strber= new StringBuilder();
            String line = null;
            while((line = br.readLine())!=null){
                strber.append(line+'\n');
            }
            br.close();
            in.close();
            result = strber.toString();
            if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK){
                result = "服务器异常";
            }
        } catch (Exception e){
            System.out.println("请求异常");
            throw new RuntimeException(e);
        } finally{
            post.abort();
        }
        return result;
    }
    public static void main(String[] args) throws Exception{
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("dealer_id", "填商户代码由综合服务平台分配");
        headerMap.put("request_id", "每个request的id");

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
        String url = "https://api-jiesuan.yunzhanghu.com/api/payment/v1/order-realtime";
        post(headerMap, map1, map2, url);
    }
}
