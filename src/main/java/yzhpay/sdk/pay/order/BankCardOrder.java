package yzhpay.sdk.pay.order;

import yzhpay.sdk.constant.ConfigPath;
import yzhpay.sdk.constant.OrderPrefixEnum;
import yzhpay.sdk.dto.request.BankCardOrderRequest;
import yzhpay.sdk.pay.BasePay;
import yzhpay.sdk.util.OrderUtil;
import yzhpay.sdk.util.Property;


/**
 * 银行卡实时下单
 */
public class BankCardOrder extends BasePay<BankCardOrderRequest> {

    public BankCardOrderRequest assembleParam() {
        return BankCardOrderRequest.builder()
                .order_id(OrderUtil.getOrderId(OrderPrefixEnum.BANK_CARD_ORDER.getValue()))
                .dealer_id(Property.getProperties(ConfigPath.YZH_DEALERID))
                .broker_id(Property.getProperties(ConfigPath.YZH_BROKERID))
                .real_name("张三")
                .card_no("6228880199872220")
                .phone_no("13488795491")
                .id_card("5326123123123211")
                .pay("100000.00")
                .pay_remark("测试数据")
                .notify_url("https://www.baidu.com")
                .build();
    }

}
