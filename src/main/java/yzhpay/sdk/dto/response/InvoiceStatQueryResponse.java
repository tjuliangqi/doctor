package yzhpay.sdk.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvoiceStatQueryResponse {

    /**
     * 商户ID
     */
    private String dealer_id;

    /**
     * 代征主体ID
     */
    private String broker_id;

    /**
     * 已开发票金额
     */
    private String invoiced;

    /**
     * 待开发票金额
     */
    private String not_invoiced;

}
