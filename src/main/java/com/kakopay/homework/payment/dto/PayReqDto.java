package com.kakopay.homework.payment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayReqDto {

    private String payId;
    private Long payAmount;
    private Long payVat;
    private String installmentMonths;
    private String cardData;

}
