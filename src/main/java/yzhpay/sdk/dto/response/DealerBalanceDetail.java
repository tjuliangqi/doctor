package yzhpay.sdk.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class DealerBalanceDetail {

    /**
     * 代征主体ID
     */
    private String broker_id;

    /**
     * 银行卡余额
     */
    private String bank_card_balance;

    /**
     * 是否开通银行卡通道
     */
    private boolean is_bank_card;

    /**
     * 支付宝余额
     */
    private String alipay_balance;

    /**
     * 是否开通支付宝通道
     */
    private boolean is_alipay;

    /**
     * 微信余额
     */
    private String wxpay_balance;

    /**
     * 是否开通微信通道
     */
    private boolean is_wxpay;

    /**
     * 服务费返点余额
     */
    private String rebate_fee_balance;

}
