package com.kakopay.homework_payment.controller.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class PayResVo {
    private String cardNum;
}
