package yzhpay.sdk.pay.verify;

import yzhpay.sdk.dto.request.ThreeFactorVerifyRequest;
import yzhpay.sdk.pay.BasePay;

public class ThreeFactorVerify extends BasePay<ThreeFactorVerifyRequest> {
    public ThreeFactorVerifyRequest assembleParam() {
        return ThreeFactorVerifyRequest.builder()
                .real_name("张三")
                .id_card("2341242341234124")
                .card_no("9037258375085093")
                .build();
    }
}
