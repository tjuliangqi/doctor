package yzhpay.sdk.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class DailyOrderFileRequest {

    /**
     * 流水时间
     */
    private String order_date;

}
