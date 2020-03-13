package yzhpay.sdk.pay.order;

import yzhpay.sdk.constant.ChannelEnum;
import yzhpay.sdk.constant.ConfigPath;
import yzhpay.sdk.dto.request.CancelOrderRequest;
import yzhpay.sdk.pay.BasePay;
import yzhpay.sdk.util.Property;

public class CancelOrder extends BasePay<CancelOrderRequest> {
    public CancelOrderRequest assembleParam() {
        return CancelOrderRequest.builder()
                .dealer_id(Property.getProperties(ConfigPath.YZH_DEALERID))
                .broker_id(Property.getProperties(ConfigPath.YZH_BROKERID))
                .order_id("123412341234")
                .channel(ChannelEnum.BANKCARD.getValue())
                .build();
    }
}
