package yzhpay.sdk.constant;

public enum OrderPrefixEnum {

    BANK_CARD_ORDER("bco"),
    ALIPAY_ORDER("alo"),
    WXPAY_ORDER("wxo");

    private String value;

    OrderPrefixEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
