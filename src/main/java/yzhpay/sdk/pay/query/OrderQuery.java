package yzhpay.sdk.pay.query;

import yzhpay.sdk.constant.ChannelEnum;
import yzhpay.sdk.dto.request.OrderQueryRequest;
import yzhpay.sdk.pay.BasePay;

public class OrderQuery extends BasePay<OrderQueryRequest> {

    public OrderQueryRequest assembleParam() {
        return OrderQueryRequest.builder()
                .order_id("384035607703241283")
                .channel(ChannelEnum.BANKCARD.getValue())
                .data_type("encryption")
                .build();
    }

}
