package yzhpay.sdk.pay.verify;

import yzhpay.sdk.dto.request.FourFactorVerifyRequest;
import yzhpay.sdk.pay.BasePay;

public class FourFactorVerify extends BasePay<FourFactorVerifyRequest> {
    public FourFactorVerifyRequest assembleParam() {
        return FourFactorVerifyRequest.builder()
                .real_name("张三")
                .id_card("2341242341234124")
                .card_no("9037258375085093")
                .mobile("13333333333")
                .build();
    }
}
