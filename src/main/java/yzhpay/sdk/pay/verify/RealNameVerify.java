package yzhpay.sdk.pay.verify;

import yzhpay.sdk.dto.request.RealNameVerifyRequest;
import yzhpay.sdk.pay.BasePay;

public class RealNameVerify extends BasePay<RealNameVerifyRequest> {
    public RealNameVerifyRequest assembleParam() {
        return RealNameVerifyRequest.builder()
                .real_name("张立铮")
                .id_card("1234123412341234")
                .build();
    }
}
