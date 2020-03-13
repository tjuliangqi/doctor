package yzhpay.sdk.pay.upload;

import yzhpay.sdk.constant.CardTypeEnum;
import yzhpay.sdk.constant.ConfigPath;
import yzhpay.sdk.dto.request.UploadExemptedInfoListRequest;
import yzhpay.sdk.pay.BasePay;
import yzhpay.sdk.util.Property;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UploadExemptedInfoList extends BasePay<UploadExemptedInfoListRequest> {
    public UploadExemptedInfoListRequest assembleParam() {
        return UploadExemptedInfoListRequest.builder()
                .id_card("EA3456789")
                .card_type(CardTypeEnum.PASSPORT.getValue())
                .real_name("张三")
                .comment_apply("一条记录")
                .broker_id(Property.getProperties(ConfigPath.YZH_BROKERID))
                .dealer_id(Property.getProperties(ConfigPath.YZH_DEALERID))
                .user_images(readImages())
                .country("CHN")
                .birthday("19900101")
                .gender("男")
                .notify_url("http://www.baidu.com")
                .ref("1234qwer")
                .build();
    }

    private String[] readImages(){
        String imagePath = "/Users/yunzhanghu304/javaSpace/java_sdk_v0.0.1/src/main/resources/image/images.png";
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imagePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return new String[]{encoder.encode(data)};//返回Base64编码过的字节数组字符串
    }
}
