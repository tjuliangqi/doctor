package yzhpay.sdk.dto.notify;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadExemptedInfoListNotify {

    private String broker_id;

    private String dealer_id;

    private String real_name;

    private String id_card;

    /**
     * status有两种状态,"pass" 和 "reject"
     */
    private String status;

    /**
     * 接口上传信息时的ref
     */
    private String ref;

    private String comment;

}
