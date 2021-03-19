package com.kakopay.homework.payment.controller.vo;

import lombok.*;

@Builder
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PayResVo {
    private String cardNum;
}
