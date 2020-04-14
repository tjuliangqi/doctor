package yzhpay.sdk.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ThreeFactorVerifyRequest {

    /**
     * 银行卡号
     */
    private String card_no;

    /**
     * 身份证号
     */
    private String id_card;

    /**
     * 开户人姓名
     */
    private String real_name;

}
