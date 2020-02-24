package cn.tju.doctor.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class numberUtils {
    public static  String getOrderNo(){
        String orderNo = "" ;
        int random = (int) (Math.random()*9+1);
        String valueOf = String.valueOf(random);
        //生成uuid的hashCode值
        int hashCode = UUID.randomUUID().toString().hashCode();
        //可能为负数
        if(hashCode<0){
            hashCode = -hashCode;
        }
        String value = valueOf + String.format("%015d", hashCode);
        String sdf = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
        orderNo = value + sdf ;
        return orderNo ;
    }
}
