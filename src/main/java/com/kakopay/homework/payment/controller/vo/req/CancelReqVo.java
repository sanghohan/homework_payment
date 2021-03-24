package com.kakopay.homework.payment.controller.vo.req;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@ToString
@Builder
public class CancelReqVo {

    @NotNull
    @NotBlank
    @Size(min = 20, max = 20, message = "txId length must be 20")
    private String txId;

    @Min(100)
    @Max(1000000000)
    private Integer cancelAmount;

    private Integer cancelVat;
}
