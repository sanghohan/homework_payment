package com.kakopay.homework.payment.runtime.Exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PayException extends Exception {

    private String code;
    private String [] args;

    public PayException(String code, String... args) {
        this.code = code;
        this.args = args;
    }
}
