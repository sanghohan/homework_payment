package com.kakopay.homework.payment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class CardDataDto {

    private String cardNum;
    private String validPeriod;
    private String cvc;
}
