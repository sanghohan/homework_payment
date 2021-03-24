package com.kakopay.homework.payment.util;

import com.kakopay.homework.payment.controller.vo.res.CardDataVo;
import com.kakopay.homework.payment.dto.CardDataDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class PayDataUtilTest {

    @Test
    @DisplayName("txId 중복 생성 테스트")
    void getPayIdTest() {

        List<String> checkList = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            String txId = PayDataUtil.generateTxId();
            assertThat(txId.length()).isEqualTo(20);

            if(i>0 && checkList.contains(txId))
                Assertions.fail();

            checkList.add(txId);
            log.debug("line number {} : {}", i+1, txId);
        }
    }

    @Test
    @DisplayName("카드데이터 생성 테스트")
    void getCardDataTest() {

        String cardData = PayDataUtil.getCardStringData("cardNum", "validPeriod", "cvc");
        assertThat(cardData.equals("cardNum|validPeriod|cvc"));
    }

    @Test
    @DisplayName("카드데이터 암호화 테스트")
    void getCardDataEncTest() throws Exception{

        String cardData = PayDataUtil.getEncCardStringData("15126134125", "0922", "888");
        log.debug(cardData);

    }

    @Test
    @DisplayName("카드데이터 복호화 테스트")
    void getCardDataDecTest() throws Exception{

        CardDataDto cardDataDto = PayDataUtil.getDecCardStringData("Vr9ggh4LBWd7q99zUAH5DNl5B//W/5xdbjBslXC8n74=");
        log.debug(cardDataDto.toString());

    }

    @Test
    @DisplayName("카드데이터 마스킹 테스트")
    void getCardDataMaskingTest() throws Exception {

        CardDataVo cardDataVo = PayDataUtil.getMaskingCardDataObjFromEnc("JqBXBn0nMHHD62JQBeIzYtCafBxy1iwpggaLR5g/HSI=");
        log.debug(cardDataVo.toString());

    }

    @Test
    @DisplayName("카드데이터 마스킹 테스트2")
    void getCardDataObjMaskingTest() throws Exception {
        CardDataDto cardDataDto = CardDataDto.builder()
                .cardNum("15126134125")
                .validPeriod("0922")
                .cvc("888")
                .build();

        CardDataVo cardDataVo = PayDataUtil.getMaskingCardDataObj(cardDataDto);
        log.debug(cardDataVo.toString());

    }


}