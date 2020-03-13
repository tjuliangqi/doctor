package yzhpay.sdk.pay.query;

import yzhpay.sdk.dto.request.RechargeRecordQueryRequest;
import yzhpay.sdk.pay.BasePay;

public class RechargeRecordQuery extends BasePay<RechargeRecordQueryRequest> {
    public RechargeRecordQueryRequest assembleParam() {
        return RechargeRecordQueryRequest.builder()
                .begin_at("2019-07-30")
                .end_at("2019-08-29")
                .build();
    }
}
