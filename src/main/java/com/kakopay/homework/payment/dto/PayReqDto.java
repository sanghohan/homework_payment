package com.kakopay.homework.payment.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PayReqDto {

    private String payId;
    private String cardData;

}
