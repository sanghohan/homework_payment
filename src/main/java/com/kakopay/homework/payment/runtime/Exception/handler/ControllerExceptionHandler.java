package com.kakopay.homework.payment.runtime.Exception.handler;

import com.kakopay.homework.payment.runtime.Exception.PayException;
import com.kakopay.homework.payment.runtime.model.PayErrorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<PayErrorVo> handleInternalServerError(Exception e) {

        log.error("==================== handlerRuntimeException ====================");
        e.printStackTrace();
        PayErrorVo responseBody = PayErrorVo.serverErrorBuilder().httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({PayException.class})
    public ResponseEntity<PayErrorVo> handleInternalCheckedError(PayException e) {

        log.error("==================== PayException ====================");
        e.printStackTrace();
        PayErrorVo responseBody = PayErrorVo.exceptionBuilder()
                .code(e.getCode())
                .message(messageSource.getMessage(e.getCode(), e.getArgs(), Locale.KOREA))
                .build();
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
