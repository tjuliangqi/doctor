package yzhpay.sdk.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadExemptedInfoListRequest {

    /**
     * 护照、港澳台居民居住证等
     */
    private String id_card;

    /**
     * 证件类型码
     */
    private String card_type;

    /**
     * 姓名
     */
    private String real_name;

    /**
     * 申请备注
     */
    private String comment_apply;

    /**
     * 代征主体ID
     */
    private String broker_id;

    /**
     * 商户ID
     */
    private String dealer_id;

    /**
     * 人员信息图片
     */
    private String[] user_images;

    /**
     * 国家代码
     */
    private String country;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 性别
     */
    private String gender;

    /**
     * 回调地址
     */
    private String notify_url;

    /**
     * 唯一流水号，回调时会附带
     */
    private String ref;

}
