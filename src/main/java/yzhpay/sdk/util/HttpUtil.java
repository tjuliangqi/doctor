package yzhpay.sdk.util;

import yzhpay.sdk.constant.ConfigPath;
import yzhpay.sdk.constant.DataDict;
import yzhpay.sdk.constant.XmlData;
import yzhpay.sdk.dto.request.Request;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http请求工具类
 */
public class HttpUtil {

    private static final String CONTENT_TYPE_JSON = "application/x-www-form-urlencoded";
    private static final String CHARSET = "UTF-8";

    /**
     * post 请求
     * @param request
     * @param url
     * @return
     * @throws Exception
     */
    public static Map<String, Object> post(Request request, String url) throws Exception {
        //签名
        String plain = getPlain(request);
        request.setSign(sign(plain));

        return postMethod(request, url);
    }

    /**
     * post请求
     *
     * @param request
     * @param url
     * @return
     * @throws Exception
     * @Description (TODO这里用一句话描述这个方法的作用)
     */
    private static Map<String, Object> postMethod(Request request, String url) throws Exception {
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("HttpUtil error url:" + url);
        }
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(DataDict.CONNECTTIMEOUT)
                .setSocketTimeout(DataDict.SOCKETTIMEOUT)
                .build();
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(),
                NoopHostnameVerifier.INSTANCE
        );
        CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLSocketFactory(sslConnectionSocketFactory).setDefaultRequestConfig(config).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE_JSON);
        httpPost.setHeader(XmlData.DEALER_ID, Property.getProperties(ConfigPath.YZH_DEALERID));
        httpPost.setHeader(XmlData.REQUEST_ID, StringUtils.getRequestId());

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //200_ifaceCode 200表示为EBS系统调用RESTFUL服务。
        params.add(new BasicNameValuePair(XmlData.MESS,  request.getMess()));
        params.add(new BasicNameValuePair(XmlData.TIMESTAMP, request.getTimestamp()+""));
        params.add(new BasicNameValuePair(XmlData.SIGN_TYPE, request.getSign_type()));
        params.add(new BasicNameValuePair(XmlData.DATA, request.getData()));
        params.add(new BasicNameValuePair(XmlData.SIGN, request.getSign()));

        CloseableHttpResponse response = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            String result = null;
            if (statusCode != DataDict.STATUS_200) {
                if (statusCode == DataDict.STATUS_302) {
                    result = response.getHeaders("location")[0].getValue();
                } else {
                    httpPost.abort();
                    throw new RuntimeException("HttpClient,error status code :" + statusCode);
                }
            } else {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, CHARSET);
                }
                Header[] headers = response.getAllHeaders();
                EntityUtils.consume(entity);
                map.put(XmlData.HEADER, headers);
            }
            map.put(XmlData.STATUSCODE, statusCode);
            map.put(XmlData.DATA, result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return map;
    }

    /**
     * GET请求
     * @param request
     * @param url
     * @return
     * @throws Exception
     */
    public static Map<String, Object> get(Request request, String url) throws Exception {
        //签名
        String plain = getPlain(request);
        request.setSign(sign(plain));
        String plainEncode = getPlainEncode(request);

        return get(url + "?" + plainEncode);
    }

    /**
     * get请求
     *
     * @param url
     * @return
     * @throws Exception
     * @Description (TODO这里用一句话描述这个方法的作用)
     */
    private static Map<String, Object> get(String url) throws Exception {
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("HttpUtil error url:" + url);
        }
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(DataDict.CONNECTTIMEOUT)
                .setSocketTimeout(DataDict.SOCKETTIMEOUT)
                .build();
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(),
                NoopHostnameVerifier.INSTANCE
        );
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setSSLSocketFactory(sslConnectionSocketFactory).setDefaultRequestConfig(config).build();
        HttpGet httpget = new HttpGet(url);

        httpget.setHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE_JSON);
        httpget.setHeader(XmlData.DEALER_ID, Property.getProperties(ConfigPath.YZH_DEALERID));
        httpget.setHeader(XmlData.REQUEST_ID, StringUtils.getRequestId());

        CloseableHttpResponse response = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 执行get请求.
            response = httpClient.execute(httpget);
            String result = null;
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != DataDict.STATUS_200) {
                if (statusCode == DataDict.STATUS_302) {
                    result = response.getHeaders("location")[0].getValue();
                } else {
                    httpget.abort();
                    throw new RuntimeException("HttpClient,error status code :" + statusCode);
                }
            } else {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, CHARSET);
                }
                Header[] headers = response.getAllHeaders();
                EntityUtils.consume(entity);
                map.put(XmlData.HEADER, headers);
            }
            map.put(XmlData.STATUSCODE, statusCode);
            map.put(XmlData.DATA, result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return map;
    }

    /**
     * sha256_HMAC加密
     *
     * @param plain 消息
     * @return 加密后字符串
     */
    private static String sign(String plain) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(Property.getProperties(ConfigPath.YZH_APPKEY).getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return byteArrayToHexString(sha256_HMAC.doFinal(plain.getBytes()));
    }

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    private static String getPlain(Request request) {
        return new StringBuffer("data=")
                .append(request.getData())
                .append("&mess=")
                .append(request.getMess())
                .append("&timestamp=")
                .append(request.getTimestamp())
                .append("&key=")
                .append(Property.getProperties(ConfigPath.YZH_APPKEY))
                .toString();
    }

    private static String getPlainEncode(Request request) throws Exception {
        return new StringBuffer("data=")
                .append(URLEncoder.encode(request.getData(), CHARSET))
                .append("&mess=")
                .append(URLEncoder.encode(request.getMess(), CHARSET))
                .append("&timestamp=")
                .append(request.getTimestamp())
                .append("&sign_type=")
                .append(URLEncoder.encode(request.getSign_type(), CHARSET))
                .append("&sign=")
                .append(URLEncoder.encode(request.getSign(), CHARSET))
                .toString();
    }

}
