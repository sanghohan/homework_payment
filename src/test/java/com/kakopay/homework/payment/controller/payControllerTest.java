package com.kakopay.homework.payment.controller;

import com.kakopay.homework.payment.controller.vo.CancelReqVo;
import com.kakopay.homework.payment.controller.vo.PayReqVo;
import com.kakopay.homework.payment.controller.vo.StringData;
import org.junit.jupiter.api.*;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class payControllerTest extends BaseTest {

    private static final String PAY_URL = "/payments/v1/pay";
    private static final String CANCEL_URL = "/payments/v1/cancel";

    private static PayReqVo payReqVo;
    private CancelReqVo cancelReqVo;

    ResultActions payResultActions;

    @BeforeAll
    static void setUp() {
        payReqVo = PayReqVo.builder()
                .cardNum("151361324432")
                .installmentMonths(0)
                .validPeriod("0522")
                .cvc("123")
                .payAmount(11000)
                .vat(1000)
                .build();

    }

    @Test
    @DisplayName("결제 테스트")
    @BeforeEach
    void payTest() throws Exception {
         payResultActions = assertPostResult(PAY_URL, payReqVo, status().isOk());
    }

    @Nested
    class Cancel {
        @Test
        @DisplayName("전체 취소 테스트")
        void cancelTest() throws Exception {

            StringData payResponse = objectMapper.readValue(payResultActions.andReturn().getResponse().getContentAsString(),
                    StringData.class);

            cancelReqVo = CancelReqVo.builder()
                    .txId(payResponse.getTxId())
                    .cancelAmount(11000)
                    .cancelVat(1000)
                    .build();

            ResultActions cancelActions = assertPostResult(CANCEL_URL, cancelReqVo, status().isOk());

        }

        @Test
        @DisplayName("부분 취소 테스트")
        void partiallyCancelTest() throws Exception {

            StringData payResponse = objectMapper.readValue(payResultActions.andReturn().getResponse().getContentAsString(),
                    StringData.class);

            cancelReqVo = CancelReqVo.builder()
                    .txId(payResponse.getTxId())
                    .cancelAmount(12000)
                    .cancelVat(1000)
                    .build();

            ResultActions cancelActions = assertPostResult(CANCEL_URL, cancelReqVo, status().isOk());

        }

    }





}
