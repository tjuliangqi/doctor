package yzhpay.sdk.dto.request;

import yzhpay.sdk.constant.ConfigPath;
import yzhpay.sdk.util.Property;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
public class RequestHeader {

    /**
     * 商户号
     **/
    private String dealer_id;

    /**
     * 请求id（唯一值）
     **/
    private String request_id;

    private static RequestHeader requestHeader = RequestHeader.builder().dealer_id(Property.getProperties(ConfigPath.YZH_DEALERID)).build();

    public static RequestHeader getInstance() {
        requestHeader.setRequest_id(UUID.randomUUID().toString());

        return requestHeader;
    }

}
