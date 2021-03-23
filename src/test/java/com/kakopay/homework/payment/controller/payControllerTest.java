package com.kakopay.homework.payment.controller;

import com.kakopay.homework.payment.controller.vo.CancelReqVo;
import com.kakopay.homework.payment.controller.vo.PayReqVo;
import com.kakopay.homework.payment.controller.vo.StringData;
import org.junit.jupiter.api.*;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class payControllerTest extends BaseTest {

    private static final String PAY_URL = "/payments/v1/pay";
    private static final String CANCEL_URL = "/payments/v1/cancel";

    private static PayReqVo payReqVo1;
    private static PayReqVo payReqVo2;
    private static PayReqVo payReqVo3;
    private CancelReqVo cancelReqVo;

    ResultActions payResultActions1;
    ResultActions payResultActions2;
    ResultActions payResultActions3;

    @BeforeAll
    static void setUp() {
        payReqVo1 = PayReqVo.builder()
                .cardNum("151361324432")
                .installmentMonths(0)
                .validPeriod("0522")
                .cvc("123")
                .payAmount(11000)
                .vat(1000)
                .build();

        payReqVo2 = PayReqVo.builder()
                .cardNum("151231411")
                .installmentMonths(0)
                .validPeriod("0523")
                .cvc("555")
                .payAmount(20000)
                .vat(909)
                .build();

        payReqVo3 = PayReqVo.builder()
                .cardNum("151231412")
                .installmentMonths(0)
                .validPeriod("0524")
                .cvc("556")
                .payAmount(20000)
                .build();

    }

    @Test
    @DisplayName("결제 테스트")
    @BeforeEach
    void payTest() throws Exception {
        payResultActions1 = assertPostResult(PAY_URL, payReqVo1, status().isOk());
        payResultActions2 = assertPostResult(PAY_URL, payReqVo2, status().isOk());
        payResultActions3 = assertPostResult(PAY_URL, payReqVo3, status().isOk());
    }

    @Nested
    class Cancel {
        @Test
        @DisplayName("전체 취소 테스트")
        void cancelTest() throws Exception {

            StringData payResponse = objectMapper.readValue(payResultActions1.andReturn().getResponse().getContentAsString(),
                    StringData.class);

            cancelReqVo = CancelReqVo.builder()
                    .txId(payResponse.getTxId())
                    .cancelAmount(11000)
                    .cancelVat(1000)
                    .build();

            assertPostResult(CANCEL_URL, cancelReqVo, status().isOk());
            //TODO:취소결제 조회 테스트 추가 해야함

        }

        @Test
        @DisplayName("부분 취소 Test Case1")
        void partiallyCancelTest1() throws Exception {

            StringData payResponse = objectMapper.readValue(payResultActions1.andReturn().getResponse().getContentAsString(),
                    StringData.class);

            cancelReqVo = CancelReqVo.builder()
                    .txId(payResponse.getTxId())
                    .cancelAmount(1100)
                    .cancelVat(100)
                    .build();

            assertPostResult(CANCEL_URL, cancelReqVo, status().isOk());

            cancelReqVo = CancelReqVo.builder()
                    .txId(payResponse.getTxId())
                    .cancelAmount(3300)
                    .build();

            assertPostResult(CANCEL_URL, cancelReqVo, status().isOk());

            cancelReqVo = CancelReqVo.builder()
                    .txId(payResponse.getTxId())
                    .cancelAmount(7000)
                    .build();

            ResultActions resultActions1 = assertPostResult(CANCEL_URL, cancelReqVo, status().isBadRequest());
            resultActions1.andExpect(jsonPath("code").value("PAY_3003"));




            cancelReqVo = CancelReqVo.builder()
                    .txId(payResponse.getTxId())
                    .cancelAmount(6600)
                    .cancelVat(700)
                    .build();

            ResultActions resultActions2 = assertPostResult(CANCEL_URL, cancelReqVo, status().isBadRequest());
            resultActions2.andExpect(jsonPath("code").value("PAY_3004"));


            cancelReqVo = CancelReqVo.builder()
                    .txId(payResponse.getTxId())
                    .cancelAmount(6600)
                    .cancelVat(600)
                    .build();

            assertPostResult(CANCEL_URL, cancelReqVo, status().isOk());


            cancelReqVo = CancelReqVo.builder()
                    .txId(payResponse.getTxId())
                    .cancelAmount(100)
                    .build();

            ResultActions resultActions3 = assertPostResult(CANCEL_URL, cancelReqVo, status().isBadRequest());
            resultActions3.andExpect(jsonPath("code").value("PAY_3001"));

        }

        @Test
        @DisplayName("부분 취소 Test Case2")
        void partiallyCancelTest2() throws Exception {

            StringData payResponse = objectMapper.readValue(payResultActions2.andReturn().getResponse().getContentAsString(),
                    StringData.class);

            cancelReqVo = CancelReqVo.builder()
                    .txId(payResponse.getTxId())
                    .cancelAmount(10000)
                    .cancelVat(0)
                    .build();

            assertPostResult(CANCEL_URL, cancelReqVo, status().isOk());

            cancelReqVo = CancelReqVo.builder()
                    .txId(payResponse.getTxId())
                    .cancelAmount(10000)
                    .cancelVat(0)
                    .build();

            ResultActions resultActions1 = assertPostResult(CANCEL_URL, cancelReqVo, status().isBadRequest());
            resultActions1.andExpect(jsonPath("code").value("PAY_3005"));

            cancelReqVo = CancelReqVo.builder()
                    .txId(payResponse.getTxId())
                    .cancelAmount(10000)
                    .cancelAmount(909)
                    .build();

            assertPostResult(CANCEL_URL, cancelReqVo, status().isOk());

        }

        @Test
        @DisplayName("부분 취소 Test Case3")
        void partiallyCancelTest3() throws Exception {

            StringData payResponse = objectMapper.readValue(payResultActions3.andReturn().getResponse().getContentAsString(),
                    StringData.class);

            cancelReqVo = CancelReqVo.builder()
                    .txId(payResponse.getTxId())
                    .cancelAmount(10000)
                    .cancelVat(1000)
                    .build();

            assertPostResult(CANCEL_URL, cancelReqVo, status().isOk());

            cancelReqVo = CancelReqVo.builder()
                    .txId(payResponse.getTxId())
                    .cancelAmount(10000)
                    .cancelVat(909)
                    .build();

            ResultActions resultActions1 = assertPostResult(CANCEL_URL, cancelReqVo, status().isBadRequest());
            resultActions1.andExpect(jsonPath("code").value("PAY_3004"));

            cancelReqVo = CancelReqVo.builder()
                    .txId(payResponse.getTxId())
                    .cancelAmount(10000)
                    .build();

            assertPostResult(CANCEL_URL, cancelReqVo, status().isOk());

        }

    }





}
