package yzhpay.sdk.pay.query;

import yzhpay.sdk.dto.request.DailyOrderFileRequest;
import yzhpay.sdk.pay.BasePay;

public class DailyOrderFileQuery extends BasePay<DailyOrderFileRequest> {

    public DailyOrderFileRequest assembleParam() {
        return DailyOrderFileRequest.builder()
                .order_date("2019-08-29")
                .build();
    }

}
