package com.kakopay.homework.payment.controller;

import com.kakopay.homework.payment.controller.vo.PayErrorVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class PayControllerAdvice {

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class, EntityNotFoundException.class})
    public ResponseEntity<PayErrorVo> handleBadRequest() {
        PayErrorVo responseBody = PayErrorVo.builder()
            .httpStatus(HttpStatus.BAD_REQUEST).build();

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<PayErrorVo> handleInternalServerError() {
        PayErrorVo responseBody = PayErrorVo.builder()
            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();

        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
