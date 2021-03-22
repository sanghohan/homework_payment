package com.kakopay.homework.payment.dto;

import com.kakopay.homework.payment.controller.vo.PayReqVo;
import com.kakopay.homework.payment.util.PayDataUtil;
import lombok.*;

@Builder
@Getter
@ToString
public class PayDto {

    private String txId;
    private Integer payAmount;
    private Integer payVat;
    private Integer installmentMonths;
    private String cardData;

    public static PayDto get(PayReqVo payReqVo) {

        return PayDto.builder()
                .txId(PayDataUtil.generateTxId())
                .payAmount(payReqVo.getPayAmount())
                .payVat(payReqVo.getVat())
                .installmentMonths(payReqVo.getInstallmentMonths())
                .cardData(PayDataUtil.getCardData(payReqVo.getCardNum(), payReqVo.getValidPeriod(), payReqVo.getCvc()))
                .build();
    }

}
