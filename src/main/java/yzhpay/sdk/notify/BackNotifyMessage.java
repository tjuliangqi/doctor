package yzhpay.sdk.notify;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class BackNotifyMessage {

    private String notify_id;

    private String notify_time;

    private BackNotifyData data;

}
