package yzhpay.sdk.pay.query;

import yzhpay.sdk.constant.ConfigPath;
import yzhpay.sdk.dto.request.DealerBalanceRequest;
import yzhpay.sdk.pay.BasePay;
import yzhpay.sdk.util.Property;

public class DealerBalanceQuery extends BasePay<DealerBalanceRequest> {

    public DealerBalanceRequest assembleParam() {
        return DealerBalanceRequest.builder()
                .dealer_id(Property.getProperties(ConfigPath.YZH_DEALERID))
                .build();
    }

}
