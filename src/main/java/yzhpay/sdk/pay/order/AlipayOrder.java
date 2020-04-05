package yzhpay.sdk.pay.order;

import yzhpay.sdk.constant.CheckNameEnum;
import yzhpay.sdk.constant.ConfigPath;
import yzhpay.sdk.constant.OrderPrefixEnum;
import yzhpay.sdk.dto.request.AlipayOrderRequest;
import yzhpay.sdk.pay.BasePay;
import yzhpay.sdk.util.OrderUtil;
import yzhpay.sdk.util.Property;

/**
 * 支付宝实时下单
 */
public class AlipayOrder extends BasePay<AlipayOrderRequest> {
    public AlipayOrderRequest assembleParam() {
        return AlipayOrderRequest.builder()
                .order_id(OrderUtil.getOrderId(OrderPrefixEnum.BANK_CARD_ORDER.getValue()))
                .dealer_id(Property.getProperties(ConfigPath.YZH_DEALERID))
                .broker_id(Property.getProperties(ConfigPath.YZH_BROKERID))
                .real_name("梁祺")
                .card_no("13835427123")
                .id_card("140729199412060070")
                .pay("1.00")
                .pay_remark("测试数据")
                .check_name(CheckNameEnum.NOCHECK.getValue())
//                .notify_url("https://www.baidu.com")
                .build();
    }
}
