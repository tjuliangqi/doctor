package yzhpay.sdk.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class OrderQueryRequest {

    /**
     * 商户订单号
     * **/
    private String order_id;

    /**
     * 银行卡，支付宝，微信(不填时为银行卡订单查询)(选填)
     */
    private String channel;

    /**
     * 如果为encryption，则对返回的data进行加密(选填)
     */
    private String data_type;

}
