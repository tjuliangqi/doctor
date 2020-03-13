package yzhpay.sdk.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class WxpayOrderRequest {
    /**
     * 商户订单号，由商户保持唯一性(必填)，64个英文字符以内
     **/
    private String order_id;

    /**
     * 商户代码(必填)
     **/
    private String dealer_id;

    /**
     * 代征主体(必填)
     **/
    private String broker_id;

    /**
     * 银行开户姓名(必填)
     **/
    private String real_name;

    /**
     * 银行开户身份证号(必填)
     **/
    private String id_card;

    /**
     * 商户AppID下，某用户的openid(必填)
     * **/
    private String openid;

    /**
     * 打款金额(单位为元, 必填)
     **/
    private String pay;

    /**
     * 描述信息(选填)
     * **/
    private String notes;

    /**
     * 打款备注(选填，最大20个字符，一个汉字占2个字符，不允许特殊字符：' " & | @ % * ( ) - : # ￥)
     **/
    private String pay_remark;

    /**
     * 回调地址(选填，最大长度为200)
     **/
    private String notify_url;

    /**
     * 微信打款商户微信AppID(选填，最大长度为200)
     * **/
    private String wx_app_id;

    /**
     * 微信打款模式(选填，二种取值，可填 "", "redpacket")
     * **/
    private String wxpay_mode;
}
