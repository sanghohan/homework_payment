package com.kakopay.homework.payment.util;

import com.kakopay.homework.payment.entity.Payment;
import com.kakopay.homework.payment.external.linkdata.stringdata.Body;
import com.kakopay.homework.payment.external.linkdata.stringdata.Header;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class StringDataGeneratorTest {

    private Header header;
    private Body body;

    @BeforeEach
    void setUp() {

        header = Header.builder()
                .txId(PayDataUtil.generateTxId())
                .payType(Payment.PayType.PAY)
                .build();

        body = Body.builder()
                .cardNum("1551241245123")
                .installmentMonths(0)
                .validPeriod("1299")
                .cvc("101")
                .transactionAmount(125240)
                .vat(0)
                .build();


    }

    @Test
    @DisplayName("string data header 생성 테스트")
    void headerTest() {
        String result = header.getStringData();
        log.debug(result);
        log.debug("length : {}", result.length());

    }

    @Test
    @DisplayName("string data body 생성 테스트")
    void bodyTest() {
        String result = body.getStringData();
        log.debug(result);
        log.debug("length : {}", result.length());

    }
}
