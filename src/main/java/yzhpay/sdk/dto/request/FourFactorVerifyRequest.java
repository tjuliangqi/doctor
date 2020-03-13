package yzhpay.sdk.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FourFactorVerifyRequest {

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

    /**
     * 开户预留手机号
     */
    private String mobile;

}
