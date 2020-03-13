package yzhpay.sdk.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class DailyBillFileResponse {

    private String bill_download_url;

}
