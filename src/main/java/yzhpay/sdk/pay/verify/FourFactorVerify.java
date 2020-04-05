package yzhpay.sdk.pay.verify;

import yzhpay.sdk.dto.request.FourFactorVerifyRequest;
import yzhpay.sdk.pay.BasePay;

public class FourFactorVerify extends BasePay<FourFactorVerifyRequest> {
    private String realName;
    private String cardNumber;
    private String idNumber;
    private String phoneNumber;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public FourFactorVerifyRequest assembleParam() {
        return FourFactorVerifyRequest.builder()
                .real_name(getRealName())
                .id_card(getIdNumber())
                .card_no(getCardNumber())
                .mobile(getPhoneNumber())
                .build();
    }
}
