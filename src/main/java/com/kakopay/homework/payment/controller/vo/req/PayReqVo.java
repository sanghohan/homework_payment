package com.kakopay.homework.payment.controller.vo.req;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@ToString
@Builder
public class PayReqVo {

    @NotNull
    @NotBlank
    //@CreditCardNumber(message = "card number is wrong")
    private String cardNum;

    @NotNull
    @Pattern(regexp = "^(0[1-9]|1[0-2])([1-9][0-9])$", message = "Check format. MMYY")
    private String validPeriod;

    @NotNull
    @Digits(integer = 3, fraction = 0, message = "Wrong CVV")
    private String cvc;

    @Min(0)
    @Max(12)
    private Integer installmentMonths;

    @Min(100)
    @Max(1000000000)
    private Integer payAmount;

    @Min(0)
    @Max(1000000000)
    private Integer vat;

}
