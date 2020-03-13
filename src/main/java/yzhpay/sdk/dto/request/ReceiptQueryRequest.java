package yzhpay.sdk.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ReceiptQueryRequest {

    /**
     * 商户订单号
     * **/
    private String order_id;

    /**
     * 平台订单号
     * **/
    private String ref;

}
