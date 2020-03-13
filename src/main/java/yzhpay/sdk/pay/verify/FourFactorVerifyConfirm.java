package yzhpay.sdk.pay.verify;

import yzhpay.sdk.dto.request.FourFactorVerifyConfirmRequest;
import yzhpay.sdk.pay.BasePay;

public class FourFactorVerifyConfirm extends BasePay<FourFactorVerifyConfirmRequest> {
    public FourFactorVerifyConfirmRequest assembleParam() {
        return FourFactorVerifyConfirmRequest.builder()
                .real_name("张三")
                .id_card("2341242341234124")
                .card_no("9037258375085093")
                .mobile("13333333333")
                .captcha("123456")
                .ref("12341234")
                .build();
    }
}
