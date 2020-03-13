package yzhpay.sdk.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RechargeRecordQueryResponse {

    /**
     * 商户ID
     */
    private String dealer_id;

    /**
     * 代征主体ID
     */
    private String broker_id;

    /**
     * 充值记录ID
     */
    private String recharge_id;

    /**
     * 实际到账金额
     */
    private String actual_amount;

    /**
     * 充值金额
     */
    private String amount;

    /**
     * 创建时间(秒)
     */
    private String created_at;

    /**
     * 资金用途
     */
    private String recharge_channel;

}
