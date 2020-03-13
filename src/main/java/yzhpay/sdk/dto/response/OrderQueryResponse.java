package yzhpay.sdk.dto.response;

public class OrderQueryResponse {

    /**
     * 金额
     */
    private String pay;

    /**
     *
     */
    private String anchor_id;

    /**
     *
     */
    private String broker_id;

    /**
     *
     */
    private String card_no;

    /**
     *
     */
    private String dealer_id;

    /**
     *
     */
    private String id_card;

    /**
     *
     */
    private String order_id;

    /**
     *
     */
    private String phone_no;

    /**
     *
     */
    private String real_name;

    /**
     *
     */
    private String ref;

    /**
     *
     */
    private String notes;

    /**
     * 订单状态码
     */
    private int status;

    /**
     * 订单详细状态码
     */
    private int status_detail;

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
     * 恒等于0，无需处理，字段已废弃
     */
    private String fee_amount;

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
     * 代征主体打款交易流水号
     */
    private String broker_bank_bill;

    /**
     * 订单接收时间
     */
    private String created_at;

    /**
     * 订单处理时间
     */
    private String finished_time;

    /**
     * 当且仅当data_type为encryption时，返回且仅返回该加密数据字段
     */
    private String encry_data;

}
