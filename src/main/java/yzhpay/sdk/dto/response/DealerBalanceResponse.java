package yzhpay.sdk.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class DealerBalanceResponse {

    private List<DealerBalanceDetail> dealer_infos;

}
