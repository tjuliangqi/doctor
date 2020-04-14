package yzhpay.sdk.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardInfoQueryRequest {

    /**
     * 银行卡号
     */
    private String card_no;

    /**
     * 银行名称
     */
    private String bank_name;

}
