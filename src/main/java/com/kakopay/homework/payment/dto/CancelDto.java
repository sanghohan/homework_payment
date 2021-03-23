package com.kakopay.homework.payment.dto;

import com.kakopay.homework.payment.controller.vo.CancelReqVo;
import com.kakopay.homework.payment.util.PayDataUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class CancelDto {

    private String txId;
    private Integer cancelAmount;
    private Integer reqCancelVat;

    @Setter
    private Integer calculatedVat;
    private String orgPayTxId;

    public static CancelDto get(CancelReqVo cancelReqVo) {

        return CancelDto.builder()
                .txId(PayDataUtil.generateTxId())
                .orgPayTxId(cancelReqVo.getTxId())
                .cancelAmount(cancelReqVo.getCancelAmount())
                .reqCancelVat(cancelReqVo.getCancelVat())
                .build();
    }

    public Integer getCalculatedVat() {

        return PayDataUtil.getVat(reqCancelVat, cancelAmount);

    }


}
