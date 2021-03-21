package com.kakopay.homework.payment.util;

import com.kakopay.homework.payment.external.linkdata.stringdata.Body;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class StringDataGeneratorTest {

    private Body body;

    @BeforeEach
    void setUp() {
        body = Body.builder()
                .cardNum("1551241245123")
                .installmentMonths(0)
                .validPeriod("1299")
                .cvc("101")
                .transactionAmount(125240)
                .vat(0)
                .orgPayId(PayDataUtil.generatePayId())
                .build();
    }

    @Test
    @DisplayName("string 데이터 body 생성 테스트")
    void bodyTest() throws Exception {
        String result = body.getStringData();
        log.debug(result);
        log.debug("length : {}", result.length());

    }
}
