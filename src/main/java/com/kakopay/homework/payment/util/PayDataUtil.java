package com.kakopay.homework.payment.util;

import com.kakopay.homework.payment.controller.vo.CardDataVo;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.StringJoiner;

@Slf4j
public class PayDataUtil {

    public static String generatePayId() {

        return System.currentTimeMillis() + RandomString.make(7);
    }

    public static String getCardData(String cardNum, String validPeriod, String cvc) {

        return new StringJoiner("|")
                .add(cardNum)
                .add(validPeriod)
                .add(cvc).toString();

    }

    public static String getEncCardData(String combinedCardData) throws Exception {

        return AES256Cipher.getInstance().AES_Encode(combinedCardData);
    }

    public static String getEncCardData(String cardNum, String validPeriod, String cvc) throws Exception {

        String combinedCardData = getCardData(cardNum, validPeriod, cvc);
        return AES256Cipher.getInstance().AES_Encode(combinedCardData);

    }

    public static CardDataVo getDecCardData(String encCardData) throws Exception {

        if (!StringUtils.hasLength(encCardData))
            throw new Exception("wrong card data!");

        String decCardData = AES256Cipher.getInstance().AES_Decode(encCardData);
        CardDataVo cardDataVo = getCardDataObj(decCardData);

        return CardDataVo.builder()
                .cardNum(cardDataVo.getCardNum())
                .validPeriod(cardDataVo.getValidPeriod())
                .cvc(cardDataVo.getCvc())
                .build();

    }

    public static CardDataVo getCardDataObj(String cardData) throws Exception {

        log.debug("decCardData : {}", cardData);
        String[] sepCardData = cardData.split("\\|");
        log.debug("sepCardData.length : {}", sepCardData.length);

        if (sepCardData.length != 3)
            throw new Exception("wrong card data!");

        return CardDataVo.builder()
                .cardNum(sepCardData[0])
                .validPeriod(sepCardData[1])
                .cvc(sepCardData[2])
                .build();

    }

    public static Integer getVat(Integer vat, Integer amount) {

        if(ObjectUtils.isEmpty(vat))
            vat = Integer.valueOf(0);

        return vat == 0 ? Math.round(amount / 11) : vat;
    }
}
