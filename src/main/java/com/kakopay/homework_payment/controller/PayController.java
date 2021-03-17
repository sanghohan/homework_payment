package com.kakopay.homework_payment.controller;


import com.kakopay.homework_payment.controller.vo.PaymentReqVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
public class PayController {

    @PostMapping(value = "/payment", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public String pay(@Validated @RequestBody PaymentReqVo request) {

        log.debug("request object : " + request);

        return "success";
    }


}
