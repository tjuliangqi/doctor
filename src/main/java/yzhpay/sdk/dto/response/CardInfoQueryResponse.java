package yzhpay.sdk.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardInfoQueryResponse {

    /**
     * 银行代码
     */
    private String bank_code;

    /**
     * 银行名称
     */
    private String bank_name;

    /**
     * 卡类型
     */
    private String card_type;

    /**
     * 云账户是否支持向该银行打款
     */
    private String is_support;

}
