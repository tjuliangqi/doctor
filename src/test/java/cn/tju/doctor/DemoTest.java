package cn.tju.doctor;

import yzhpay.sdk.constant.ConfigPath;
import yzhpay.sdk.constant.XmlData;
import yzhpay.sdk.dto.response.*;
import yzhpay.sdk.pay.order.BankCardOrder;
import yzhpay.sdk.pay.verify.*;
import yzhpay.sdk.util.HttpUtil;
import yzhpay.sdk.util.JsonUtil;
import yzhpay.sdk.util.Property;
import yzhpay.sdk.util.StringUtils;
import org.junit.Test;

import java.util.Map;

public class DemoTest {

    public Response sysoutResult(Map<String, Object> result) {
        Response response = null;
        try {
            if("200".equals(StringUtils.trim(result.get(XmlData.STATUSCODE)))){
                response = JsonUtil.fromJson(StringUtils.trim(result.get(XmlData.DATA)), Response.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 银行卡实时下单
     */


//    @Test
//    public void alipayOrder() throws Exception {
//        Map<String, Object> result = HttpUtil.post(new AlipayOrder().assembleRequest(), Property.getUrl(ConfigPath.YZH_ALIPAY_REAL_TIME_ORDER));
//        sysoutResult(result);
//    }
//
//    @Test
//    public void wxpayOrder() throws Exception {
//        Map<String, Object> result = HttpUtil.post(new WxpayOrder().assembleRequest(), Property.getUrl(ConfigPath.YZH_WXPAY_REAL_TIME_ORDER));
//        sysoutResult(result);
//    }
//
//    @Test
//    public void orderQuery() throws Exception {
//        Map<String, Object> result = HttpUtil.get(new OrderQuery().assembleRequest(), Property.getUrl(ConfigPath.YZH_ORDER_QUERY));
//        sysoutResult(result);
//    }
//
//    @Test
//    public void dealerBalanceQuery() throws Exception {
//        Map<String, Object> result = HttpUtil.get(new DealerBalanceQuery().assembleRequest(), Property.getUrl(ConfigPath.YZH_DEALER_BALANCE_QUERY));
//        sysoutResult(result);
//    }
//
//    @Test
//    public void receiptQuery() throws Exception {
//        Map<String, Object> result = HttpUtil.get(new ReceiptQuery().assembleRequest(), Property.getUrl(ConfigPath.YZH_RECEIPT_QUERY));
//        sysoutResult(result);
//    }
//
//    @Test
//    public void cancelOrder() throws Exception {
//        Map<String, Object> result = HttpUtil.post(new CancelOrder().assembleRequest(), Property.getUrl(ConfigPath.YZH_CANCEL_ORDER));
//        sysoutResult(result);
//    }
//
//    @Test
//    public void dailyOrderFileQuery() throws Exception {
//        Map<String, Object> result = HttpUtil.get(new DailyOrderFileQuery().assembleRequest(), Property.getUrl(ConfigPath.YZH_DAILY_ORDER_FILE_QUERY));
//        sysoutResult(result);
//    }
//
//    @Test
//    public void dailyBillFileQuery() throws Exception {
//        Map<String, Object> result = HttpUtil.get(new DailyBillFileQuery().assembleRequest(), Property.getUrl(ConfigPath.YZH_DAILY_BILL_FILE_QUERY));
//        sysoutResult(result);
//    }
//
//    @Test
//    public void rechargeRecordQuery() throws Exception {
//        Map<String, Object> result = HttpUtil.get(new RechargeRecordQuery().assembleRequest(), Property.getUrl(ConfigPath.YZH_RECHARGE_RECORD_QUERY));
//        sysoutResult(result);
//    }
//
//    @Test
//    public void fourFactorVerifySendMsg() throws Exception {
//        Map<String, Object> result = HttpUtil.post(new FourFactorVerifySendMsg().assembleRequest(), Property.getUrl(ConfigPath.YZH_FOUR_FACTOR_VERIFY_SEND_MSG));
//        sysoutResult(result);
//    }
//
//    @Test
//    public void fourFactorVerifyConfirm() throws Exception {
//        Map<String, Object> result = HttpUtil.post(new FourFactorVerifyConfirm().assembleRequest(), Property.getUrl(ConfigPath.YZH_FOUT_FACTOR_VERIFY_CONFIRM));
//        sysoutResult(result);
//    }
//

//
//    @Test
//    public void threeFactorVerify() throws Exception {
//        Map<String, Object> result = HttpUtil.post(new ThreeFactorVerify().assembleRequest(), Property.getUrl(ConfigPath.YZH_THREE_FACTOR_BANK_CARD_VERIFY));
//        sysoutResult(result);
//    }
//
//    @Test
//    public void realNameVerify() throws Exception {
//        Map<String, Object> result = HttpUtil.post(new RealNameVerify().assembleRequest(), Property.getUrl(ConfigPath.YZH_REAL_NAME_VERIFY));
//        sysoutResult(result);
//    }
//
//    @Test
//    public void uploadExemptedInfoList() throws Exception {
//        Map<String, Object> result = HttpUtil.post(new UploadExemptedInfoList().assembleRequest(), Property.getUrl(ConfigPath.YZH_UPLOAD_EXEMOTED_INFO_LIST));
//        sysoutResult(result);
//    }
//
//    @Test
//    public void exemptedInfoListQuery() throws Exception {
//        Map<String, Object> result = HttpUtil.post(new ExemptedInfoListQuery().assembleRequest(), Property.getUrl(ConfigPath.YZH_EXEMOTED_INFO_QUERY));
//        sysoutResult(result);
//    }
//
//    @Test
//    public void BankCardInfoQuery() throws Exception {
//        Map<String, Object> result = HttpUtil.get(new BankCardInfoQuery().assembleRequest(), Property.getUrl(ConfigPath.YZH_CARD_INFO_QUERY));
//        sysoutResult(result);
//    }
//
//    @Test
//    public void invoiceStatQuery() throws Exception {
//        Map<String, Object> result = HttpUtil.get(new InvoiceStatQuery().assembleRequest(), Property.getUrl(ConfigPath.YZH_INVOICE_STAT_QUERY));
//        sysoutResult(result);
//    }
}
