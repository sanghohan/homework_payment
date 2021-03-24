package com.kakopay.homework.payment.controller.vo.res;


import com.kakopay.homework.payment.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class PayAmountInfo {

    private int transactionAmount;
    private int vat;

    @Builder
    public PayAmountInfo(Payment payment) {

        if(payment.getPayType().equals(Payment.PayType.PAY)) {
            this.transactionAmount = payment.getPayAmount();
            this.vat = payment.getPayVat();
        } else {
            this.transactionAmount = payment.getCancelAmount();
            this.vat = payment.getCancelVat();
        }
    }


}
