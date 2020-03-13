package yzhpay.sdk.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class DailyOrderFileResponse {

    /**
     * 文件下载链接
     */
    private String order_download_url;

}
