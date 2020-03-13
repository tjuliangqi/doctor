package yzhpay.sdk.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class AlipayOrderRequest {

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
     * 银行开户卡号(必填)
     **/
    private String card_no;

    /**
     * 银行开户身份证号(必填)
     **/
    private String id_card;

    /**
     * 打款金额(单位为元, 必填)
     **/
    private String pay;

    /**
     * 打款备注(选填，最大20个字符，一个汉字占2个字符，不允许特殊字符：' " & | @ % * ( ) - : # ￥)
     **/
    private String pay_remark;

    /**
     * 校验支付宝账户姓名，可填 Check、NoCheck
     * **/
    private String check_name;

    /**
     * 回调地址(选填，最大长度为200)
     **/
    private String notify_url;

}
