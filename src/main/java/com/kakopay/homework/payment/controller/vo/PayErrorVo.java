package com.kakopay.homework.payment.controller.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class PayErrorVo {

    private String code;
    private String message;

    @Builder
    public PayErrorVo(HttpStatus httpStatus) {
        this.code = String.valueOf(httpStatus.value());
        this.message = httpStatus.getReasonPhrase();
    }

}
