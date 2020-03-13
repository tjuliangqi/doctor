package yzhpay.sdk.pay.order;

import yzhpay.sdk.constant.ConfigPath;
import yzhpay.sdk.constant.OrderPrefixEnum;
import yzhpay.sdk.dto.request.WxpayOrderRequest;
import yzhpay.sdk.pay.BasePay;
import yzhpay.sdk.util.OrderUtil;
import yzhpay.sdk.util.Property;

public class WxpayOrder extends BasePay<WxpayOrderRequest> {

    public WxpayOrderRequest assembleParam() {
        return WxpayOrderRequest.builder()
                .order_id(OrderUtil.getOrderId(OrderPrefixEnum.BANK_CARD_ORDER.getValue()))
                .dealer_id(Property.getProperties(ConfigPath.YZH_DEALERID))
                .broker_id(Property.getProperties(ConfigPath.YZH_BROKERID))
                .real_name("张三")
                .id_card("5326123123123211")
                .openid("wxpayOrder")
                .pay("100000.00")
                .notes("备注")
                .pay_remark("测试数据")
                .notify_url("https://www.baidu.com")
                .wx_app_id("")
                .wxpay_mode("wxpayOrder")
                .build();
    }

}
