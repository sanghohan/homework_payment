package com.kakopay.homework.payment.controller;

import com.kakopay.homework.payment.controller.vo.CancelReqVo;
import com.kakopay.homework.payment.controller.vo.PayReqVo;
import com.kakopay.homework.payment.controller.vo.StringData;
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
    private CancelReqVo cancelReqVo;

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

    @Test
    @DisplayName("취소 테스트")
    void cancelTest() throws Exception {
        ResultActions resultActions = assertPostResult(PAY_URL, payReqVo, status().isOk());

        StringData payResponse = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(),
                StringData.class);

        cancelReqVo = CancelReqVo.builder()
                .txId(payResponse.getTxId())
                .cancelAmount(10000)
                .cancelVat(Math.round(10000/11))
                .build();

        ResultActions cancelActions = assertPostResult(CANCEL_URL, cancelReqVo, status().isOk());

    }

}
