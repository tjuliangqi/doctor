package yzhpay.sdk.pay.notify;

import yzhpay.sdk.notify.BackNotifyMessage;
import yzhpay.sdk.util.CipherUtil;
import yzhpay.sdk.util.JsonUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReceiveNotify {

    static String key = "123456788765432112345678";

    @PostMapping(path = "/notify")
    public Object receiveNotifyMessage(@RequestBody String message) throws Exception {
        NotifyResponse notifyResponse = JsonUtil.fromJson(message, NotifyResponse.class);

        byte[] dec64 = Base64.decodeBase64(notifyResponse.getData());
        byte[] dec = CipherUtil.TripleDesDecrypt(dec64, key.getBytes("utf-8"));
        BackNotifyMessage backNotifyMessage = JsonUtil.fromJson(new String(dec), BackNotifyMessage.class);

        System.out.println(backNotifyMessage);

        return "success";
    }

}
