package yzhpay.sdk.pay.query;

import yzhpay.sdk.dto.request.ReceiptQueryRequest;
import yzhpay.sdk.pay.BasePay;

public class ReceiptQuery extends BasePay<ReceiptQueryRequest> {
    public ReceiptQueryRequest assembleParam() {
        return ReceiptQueryRequest.builder()
                .order_id("123412341234")
                .ref("432143214321")
                .build();
    }
}
