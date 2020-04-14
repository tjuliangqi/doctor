package yzhpay.sdk.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class OrderResponse {

    /**
     *
     **/
    private String pay;

    /**
     *
     **/
    private String ref;

    /**
     *
     **/
    private String order_id;

}
