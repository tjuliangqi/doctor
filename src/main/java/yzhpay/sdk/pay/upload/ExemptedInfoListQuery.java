package yzhpay.sdk.pay.upload;

import yzhpay.sdk.dto.request.ExemotedInfoListQueryRequest;
import yzhpay.sdk.pay.BasePay;

public class ExemptedInfoListQuery extends BasePay<ExemotedInfoListQueryRequest> {
    public ExemotedInfoListQueryRequest assembleParam() {
        return ExemotedInfoListQueryRequest.builder()
                .id_card("123412341234123412")
                .real_name("张三")
                .build();
    }
}
