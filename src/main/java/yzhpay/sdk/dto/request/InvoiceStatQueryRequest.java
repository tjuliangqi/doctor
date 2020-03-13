package yzhpay.sdk.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvoiceStatQueryRequest {

    /**
     * 商户id
     */
    private String dealer_id;

    /**
     * 代征主体id
     */
    private String broker_id;

    /**
     * 数字类型，按年份查询已开发票金额，不传代表当前年份
     */
    private int year;

}
