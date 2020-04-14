package yzhpay.sdk.dto.request;

import yzhpay.sdk.util.CipherUtil;
import yzhpay.sdk.util.JsonUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 示例：data=MhjTl1rWjFxHJ5e&mess=12313&timestamp=123457&sign=b6516baa210161df6f34f1efec2a7c484fd7920fed5640e066e970d8a3f01499&sign_type=sha256
 **/
@Getter
@Setter
@ToString
@Builder
public class Request<T> {

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

    public Request encData(T param) throws Exception {
        setData(CipherUtil.encData(JsonUtil.toJson(param)));
        return this;
    }
}
