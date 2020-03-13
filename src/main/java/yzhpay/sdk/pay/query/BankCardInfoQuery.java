package yzhpay.sdk.pay.query;

import yzhpay.sdk.dto.request.CardInfoQueryRequest;
import yzhpay.sdk.pay.BasePay;

public class BankCardInfoQuery extends BasePay<CardInfoQueryRequest> {

    public CardInfoQueryRequest assembleParam() {
        return CardInfoQueryRequest.builder()
                .card_no("6225880150365543")
                .bank_name("中国招商银行")
                .build();
    }

}
