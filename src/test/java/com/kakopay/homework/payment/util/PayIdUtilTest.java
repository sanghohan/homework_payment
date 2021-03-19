package com.kakopay.homework.payment.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class PayIdUtilTest {

    @Test
    @DisplayName("payId 중복 생성 테스트")
    void getPayIdTest() {

        List<String> checkList = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            String payId = PayIdUtil.getPayId();
            assertThat(payId.length()).isEqualTo(20);

            if(i>0 && checkList.contains(payId))
                Assertions.fail();

            checkList.add(payId);
            log.debug("line number {} : {}", i+1, payId);
        }
    }
}