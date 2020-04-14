package yzhpay.sdk.pay.notify;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
public class NotifyResponse {

    /**
     * 时间戳，精确到秒
     **/
    private int timestamp = Integer.parseInt(String.valueOf(new Date().getTime()/1000));

    /**
     * 签名
     **/
    private String sign;

    /**
     * 签名方式，固定值sha256
     **/
    private String sign_type = "sha256";

    /**
     * 随机数，用于签名
     **/
    private String mess;

    /**
     * 经过加密后的具体数据
     **/
    private String data;

}
