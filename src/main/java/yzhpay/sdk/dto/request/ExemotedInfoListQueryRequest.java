package yzhpay.sdk.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExemotedInfoListQueryRequest {

    /**
     * 护照、港澳台居民居住证等
     */
    private String id_card;


    /**
     * 姓名
     */
    private String real_name;

}
