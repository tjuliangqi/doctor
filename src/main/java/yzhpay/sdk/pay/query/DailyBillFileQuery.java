package yzhpay.sdk.pay.query;

import yzhpay.sdk.dto.request.DailyBillFileRequest;
import yzhpay.sdk.pay.BasePay;

public class DailyBillFileQuery extends BasePay<DailyBillFileRequest> {
    public DailyBillFileRequest assembleParam() {
        return DailyBillFileRequest.builder()
                .bill_date("2019-08-29")
                .build();
    }
}
