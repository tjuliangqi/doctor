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
    private String realName;
    private String cardNumber;
    private String idNumber;
    private String phoneNumber;
    private String payMoney;
    private String payRemark;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayRemark() {
        return payRemark;
    }

    public void setPayRemark(String payRemark) {
        this.payRemark = payRemark;
    }

    public BankCardOrderRequest assembleParam() {
        return BankCardOrderRequest.builder()
                .order_id(OrderUtil.getOrderId(OrderPrefixEnum.BANK_CARD_ORDER.getValue()))
                .dealer_id(Property.getProperties(ConfigPath.YZH_DEALERID))
                .broker_id(Property.getProperties(ConfigPath.YZH_BROKERID))
                .real_name(getRealName())
                .card_no(getCardNumber())
                .phone_no(getPhoneNumber())
                .id_card(getIdNumber())
                .pay(getPayMoney())
                .pay_remark(getPayRemark())
//                .notify_url("https://www.baidu.com")
                .build();
    }
}
