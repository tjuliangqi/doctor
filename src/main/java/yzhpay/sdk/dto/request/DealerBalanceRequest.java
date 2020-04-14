package yzhpay.sdk.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class DealerBalanceRequest {

    /**
     * 商户代码(必填)
     **/
    private String dealer_id;

}
