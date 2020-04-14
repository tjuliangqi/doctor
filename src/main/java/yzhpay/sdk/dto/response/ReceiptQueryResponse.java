package yzhpay.sdk.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ReceiptQueryResponse {

    /**
     * 链接失效时间
     */
    private String expire_time;

    /**
     * 回单名
     */
    private String file_name;

    /**
     * 电子回单的下载链接
     */
    private String url;

}
