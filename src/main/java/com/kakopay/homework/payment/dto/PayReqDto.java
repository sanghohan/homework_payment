package com.kakopay.homework.payment.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class PayReqDto {

    private String payId;
    private Long payAmount;
    private Long payVat;
    private Integer installmentMonths;
    private String cardData;

}
