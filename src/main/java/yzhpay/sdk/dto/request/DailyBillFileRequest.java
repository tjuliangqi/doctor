package yzhpay.sdk.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class DailyBillFileRequest {

    /**
     * 流水时间
     */
    private String bill_date;

}
