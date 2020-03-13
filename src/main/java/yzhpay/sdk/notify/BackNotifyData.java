package yzhpay.sdk.notify;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class BackNotifyData {

    private String pay;

    private String anchor_id;

    private String broker_id;

    private String card_no;

    private String dealer_id;

    private String id_card;

    private String order_id;

    private String phone_no;

    private String real_name;

    private String ref;

    private String notes;

    /**
     * 订单状态码
     */
    private String status;

    /**
     * 订单详细状态码
     */
    private String status_detail;

    /**
     * 状态码说明
     */
    private String status_message;

    /**
     * 状态详细状态码说明
     */
    private String status_detail_message;

    /**
     * 恒等于0，无需处理，字段已废弃
     */
    private String sys_amount;

    /**
     * 代征主体打款金额（等于pay金额）
     */
    private String broker_amount;

    /**
     * 代征主体服务费
     */
    private String broker_fee;

    /**
     * 打款备注(选填，最大20个字符，一个汉字占2个字符，不允许特殊字符：' " & | @ % * ( ) - : # ￥)
     */
    private String pay_remark;

    /**
     * 系统打款商户订单号，无需处理，字段已废弃
     */
    private String sys_wallet_ref;

    /**
     * 系统打款交易流水号，无需处理，字段已废弃
     */
    private String sys_bank_bill;

    /**
     * 代征主体打款商户订单号，无需处理，字段已废弃
     */
    private String broker_wallet_ref;

    /**
     * 代征主体打款交易流水号
     */
    private String broker_bank_bill;

    /**
     * 订单接收时间
     */
    private String created_at;

    /**
     * 订单完成时间
     */
    private String finished_time;

    /**
     * 税费
     */
    private String tax;

    /**
     * 打款平台("bankpay": 银行卡，"alipay": 支付宝，"wxpay": 微信)
     */
    private String withdraw_platform;

    /**
     * 系统打款服务费
     */
    private String sys_fee;

    /**
     * 用户服务费
     */
    private String user_fee;

}
