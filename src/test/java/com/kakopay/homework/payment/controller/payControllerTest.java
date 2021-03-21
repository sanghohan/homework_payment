package com.kakopay.homework.payment.controller;

import com.kakopay.homework.payment.controller.vo.PayReqVo;
import com.kakopay.homework.payment.repository.PaymentRepository;
import com.kakopay.homework.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class payControllerTest extends BaseTest {

    private static final String PAY_URL = "/payments/v1/pay";
    private static final String CANCEL_URL = "/payments/v1/cancel";

    private PayReqVo payReqVo;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        payReqVo = PayReqVo.builder()
                .cardNum("151361324432")
                .installmentMonths(0)
                .validPeriod("0522")
                .cvc("123")
                .payAmount(10000)
                .build();
    }

    @Test
    @DisplayName("결제 테스트")
    void payTest() throws Exception {
        ResultActions resultActions = assertPostResult(PAY_URL, payReqVo, status().isOk());
    }

}
