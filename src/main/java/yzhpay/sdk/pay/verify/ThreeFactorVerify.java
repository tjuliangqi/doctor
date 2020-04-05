package yzhpay.sdk.pay.verify;

import yzhpay.sdk.dto.request.ThreeFactorVerifyRequest;
import yzhpay.sdk.pay.BasePay;

public class ThreeFactorVerify extends BasePay<ThreeFactorVerifyRequest> {
    public ThreeFactorVerifyRequest assembleParam() {
        return ThreeFactorVerifyRequest.builder()
                .real_name("梁祺")
                .id_card("140729199412060070")
                .card_no("6227000267060459672")
                .build();
    }
}
