package com.kakopay.homework.payment.controller.vo.res;

import com.kakopay.homework.payment.entity.Payment;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class PayInfoResVo {

    private String txId;
    private CardDataVo cardDataVo;
    private Payment.PayType payType;
    private Payment.PayStatus status;
    private PayAmountInfo payAmountInfo;
    private String orgTxId;
    private LocalDateTime transactionDt;

}
