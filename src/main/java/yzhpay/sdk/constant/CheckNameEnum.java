package yzhpay.sdk.constant;

/**
 * 校验支付宝账户姓名
 * **/
public enum CheckNameEnum {

    NOCHECK("NoCheck"),
    CHECK("Check");

    private String value;

    CheckNameEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
