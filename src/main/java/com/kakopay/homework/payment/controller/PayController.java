package com.kakopay.homework.payment.controller;


import com.kakopay.homework.payment.controller.vo.CancelReqVo;
import com.kakopay.homework.payment.controller.vo.PayReqVo;
import com.kakopay.homework.payment.controller.vo.PayResVo;
import com.kakopay.homework.payment.dto.PayReqDto;
import com.kakopay.homework.payment.service.PaymentService;
import com.kakopay.homework.payment.util.PayDataUtil;
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
    public PayResVo pay(@Validated @RequestBody PayReqVo request) {

        log.debug("request object : " + request);
        PayReqDto reqDto = PayReqDto.builder()
                .payId(PayDataUtil.getPayId())
                .payAmount(request.getPayAmount())
                .payVat(request.getVat())
                .installmentMonths(request.getInstallmentMonths())
                .cardData(PayDataUtil.getCardData(request.getCardNum(), request.getValidPeriod(), request.getCvc()))
                .build();

        return paymentService.pay(reqDto);
    }

    @PostMapping(value = "/cancel", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public String cancel(@Validated @RequestBody CancelReqVo request) {

        log.debug("request object : " + request);

        return "success";
    }

    @GetMapping(value = "/{paymentId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public String getPayInfo(@PathVariable String paymentId) {

        log.debug("request paymentId : " + paymentId);

        return "success";
    }


}
