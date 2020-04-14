package yzhpay.sdk.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FourFactorVerifyResponse {

    /**
     * 交易凭证
     */
    private String ref;

}
