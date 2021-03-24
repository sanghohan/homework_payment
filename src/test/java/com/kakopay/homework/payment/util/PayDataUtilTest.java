package com.kakopay.homework.payment.util;

import com.kakopay.homework.payment.controller.vo.res.CardDataVo;
import com.kakopay.homework.payment.dto.CardDataDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
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
                Assertions.fail("duplicated txId");

            checkList.add(txId);
            log.debug("line number {} : {}", i+1, txId);
        }
    }

    @Test
    @DisplayName("카드데이터 생성 테스트")
    void getCardDataTest() {

        String cardData = PayDataUtil.getCardStringData("cardNum", "validPeriod", "cvc");
        log.debug(cardData);
        assertThat(cardData).isEqualTo("cardNum|validPeriod|cvc");
    }

    @Test
    @DisplayName("카드데이터 암호화 테스트")
    void getCardDataEncTest() throws Exception{

        String cardData = PayDataUtil.getEncCardStringData("15126134125", "0922", "888");
        log.debug(cardData);
        assertThat(cardData).isEqualTo("JqBXBn0nMHHD62JQBeIzYtCafBxy1iwpggaLR5g/HSI=");


    }

    @Test
    @DisplayName("카드데이터 복호화 테스트")
    void getCardDataDecTest() throws Exception{

        CardDataDto cardDataDto = PayDataUtil.getDecCardStringData("Vr9ggh4LBWd7q99zUAH5DNl5B//W/5xdbjBslXC8n74=");
        log.debug(cardDataDto.toString());

        assertThat(cardDataDto.getCardNum()).isEqualTo("cardNum");
        assertThat(cardDataDto.getValidPeriod()).isEqualTo("validPeriod");
        assertThat(cardDataDto.getCvc()).isEqualTo("cvc");

    }

    @Test
    @DisplayName("카드데이터 마스킹 테스트")
    void getCardDataMaskingTest() throws Exception {

        CardDataVo cardDataVo = PayDataUtil.getMaskingCardDataObjFromEnc("JqBXBn0nMHHD62JQBeIzYtCafBxy1iwpggaLR5g/HSI=");
        assertThat(cardDataVo.getCardNum()).isEqualTo("151261**125");


    }

    @Test
    @DisplayName("카드데이터 마스킹 테스트2")
    void getCardDataObjMaskingTest() throws Exception {
        CardDataDto cardDataDto = CardDataDto.builder()
                .cardNum("15126234126")
                .validPeriod("0922")
                .cvc("888")
                .build();

        CardDataVo cardDataVo = PayDataUtil.getMaskingCardDataObj(cardDataDto);
        log.debug(cardDataVo.toString());

        assertThat(cardDataVo.getCardNum()).isEqualTo("151262**126");

    }


}