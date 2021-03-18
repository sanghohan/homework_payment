package com.kakopay.homework.payment.controller.vo;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@ToString
public class PayReqVo {

    @NotNull
    @NotBlank
    @CreditCardNumber(message = "card number is wrong")
    private String cardNum;

    @NotNull
    @Pattern(regexp = "^(0[1-9]|1[0-2])([1-9][0-9])$", message = "Check format. MMYY")
    private String validPeriod;

    @NotNull
    @Digits(integer = 3, fraction = 0, message = "Wrong CVV")
    private String cvc;

    @NotNull
    @Pattern(regexp = "^(0|1)([1-12])$", message = "Check format. MM")
    private String installmentMonths;

    private Integer vat;

}
