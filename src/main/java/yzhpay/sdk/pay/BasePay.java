package yzhpay.sdk.pay;

import yzhpay.sdk.dto.request.Request;
import yzhpay.sdk.util.OrderUtil;

import java.util.Date;

public abstract class BasePay<T> {

    public Request assembleRequest() throws Exception {
        return Request.builder()
                .mess(OrderUtil.getMess())
                .timestamp(Integer.parseInt(String.valueOf(new Date().getTime()/1000)))
                .sign_type("sha256")
                .build().encData(assembleParam());
    }

    public abstract T assembleParam();

}
