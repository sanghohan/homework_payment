package com.kakopay.homework.payment.controller.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class CancelResVo {
    private String cardNum;
}
