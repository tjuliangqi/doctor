package yzhpay.sdk.pay.invoice;

import yzhpay.sdk.constant.ConfigPath;
import yzhpay.sdk.dto.request.InvoiceStatQueryRequest;
import yzhpay.sdk.pay.BasePay;
import yzhpay.sdk.util.Property;

public class InvoiceStatQuery extends BasePay<InvoiceStatQueryRequest> {
    public InvoiceStatQueryRequest assembleParam() {
        return InvoiceStatQueryRequest.builder()
                .dealer_id(Property.getProperties(ConfigPath.YZH_DEALERID))
                .broker_id(Property.getProperties(ConfigPath.YZH_BROKERID))
                .year(2019)
                .build();
    }
}
