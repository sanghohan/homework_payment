package com.kakopay.homework.payment.controller;


import com.kakopay.homework.payment.controller.vo.req.CancelReqVo;
import com.kakopay.homework.payment.controller.vo.req.PayReqVo;
import com.kakopay.homework.payment.controller.vo.res.PayInfoResVo;
import com.kakopay.homework.payment.dto.CancelDto;
import com.kakopay.homework.payment.dto.PayDto;
import com.kakopay.homework.payment.controller.vo.res.StringData;
import com.kakopay.homework.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/payments/v1")
public class PayController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/pay", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public StringData pay(@Validated @RequestBody PayReqVo request) throws Exception {

        log.debug("request object : {}", request);

        return paymentService.pay(PayDto.get(request));
    }

    @PostMapping(value = "/cancel", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public StringData cancel(@Validated @RequestBody CancelReqVo request) throws Exception {

        log.debug("request object : {}", request);

        return paymentService.cancel(CancelDto.get(request));

    }

    @GetMapping(value = "/{txId}", produces = APPLICATION_JSON_VALUE)
    public PayInfoResVo getPayInfo(@PathVariable String txId) throws Exception {

        log.debug("request txId : {}", txId);

        return paymentService.findPayInfo(txId);
    }


}
