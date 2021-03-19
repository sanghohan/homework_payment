package com.kakopay.homework.payment.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PayReqDto {

    private String payId;
    private Long payAmount;
    private Long payVat;
    private String installmentMonths;
    private String cardData;

}
