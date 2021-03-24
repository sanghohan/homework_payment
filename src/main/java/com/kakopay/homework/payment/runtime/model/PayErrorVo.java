package com.kakopay.homework.payment.runtime.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayErrorVo {

    private String code;
    private String message;

    @Builder(builderClassName = "ServerErrorBuilder", builderMethodName ="serverErrorBuilder")
    public PayErrorVo(HttpStatus httpStatus) {
        this.code = String.valueOf(httpStatus.value());
        this.message = httpStatus.getReasonPhrase();
    }

    @Builder(builderClassName = "ExceptionBuilder", builderMethodName ="exceptionBuilder")
    public PayErrorVo(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
