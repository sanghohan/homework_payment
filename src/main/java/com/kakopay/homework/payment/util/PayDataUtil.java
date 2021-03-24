package com.kakopay.homework.payment.util;

import com.kakopay.homework.payment.controller.vo.masking.MaskRequired;
import com.kakopay.homework.payment.controller.vo.masking.MaskingInfo;
import com.kakopay.homework.payment.controller.vo.res.CardDataVo;
import com.kakopay.homework.payment.dto.CardDataDto;
import com.kakopay.homework.payment.runtime.Exception.PayException;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Field;
import java.util.StringJoiner;

@Slf4j
public class PayDataUtil {

    private final static String CARD_DATA_DELIMITER = "|";
    private final static String CARD_DATA_SPLIT_REGEX = "\\" + CARD_DATA_DELIMITER;

    private static ModelMapper modelMapper = new ModelMapper();

    public static String generateTxId() {

        return System.currentTimeMillis() + RandomString.make(7);
    }

    public static String getCardStringData(String cardNum, String validPeriod, String cvc) {

        return new StringJoiner(CARD_DATA_DELIMITER)
                .add(cardNum)
                .add(validPeriod)
                .add(cvc).toString();

    }


    public static String getCardStringData(CardDataDto cardDataDto) {

        return new StringJoiner(CARD_DATA_DELIMITER)
                .add(cardDataDto.getCardNum())
                .add(cardDataDto.getValidPeriod())
                .add(cardDataDto.getCvc()).toString();

    }

    public static String getEncCardStringData(String combinedCardData) throws Exception {

        return AES128Cipher.AES_Encode(combinedCardData);
    }

    public static String getEncCardStringData(String cardNum, String validPeriod, String cvc) throws Exception {

        String combinedCardData = getCardStringData(cardNum, validPeriod, cvc);
        return AES128Cipher.AES_Encode(combinedCardData);

    }

    public static CardDataDto getDecCardStringData(String encCardData) throws Exception {

        if (StringUtils.isEmpty(encCardData))
            throw new PayException("PAY_2001", encCardData);

        String decCardData = AES128Cipher.AES_Decode(encCardData);
        CardDataDto cardDataDto = getCardDataObj(decCardData);

        return CardDataDto.builder()
                .cardNum(cardDataDto.getCardNum())
                .validPeriod(cardDataDto.getValidPeriod())
                .cvc(cardDataDto.getCvc())
                .build();

    }

    public static CardDataDto getCardDataObj(String cardData) throws Exception {

        //log.debug("decCardData : {}", cardData);
        String[] sepCardData = splitCardStringData(cardData);

        return CardDataDto.builder()
                .cardNum(sepCardData[0])
                .validPeriod(sepCardData[1])
                .cvc(sepCardData[2])
                .build();

    }

    private static String[] splitCardStringData(String cardStringData) throws Exception {

        String[] sepCardData = cardStringData.split("\\|");

        if (sepCardData.length != 3)
            throw new PayException("PAY_2001", cardStringData);

        return sepCardData;
    }

    public static CardDataVo getMaskingCardDataObjFromEnc(String encCardStringData) throws Exception {

        CardDataDto cardDataDto = getDecCardStringData(encCardStringData);
        CardDataVo cardDataVo = modelMapper.map(cardDataDto, CardDataVo.class);

        return (CardDataVo) maskingString(cardDataVo);

    }

    public static CardDataVo getMaskingCardDataObj(CardDataDto cardDataDto) throws Exception {

        CardDataVo cardDataVo = modelMapper.map(cardDataDto, CardDataVo.class);

        return (CardDataVo) maskingString(cardDataVo);

    }

    public static Object maskingString(Object obj) throws Exception {

        for (Field field : obj.getClass().getDeclaredFields()) {

            MaskRequired maskRequired = field.getAnnotation(MaskRequired.class);

            if (ObjectUtils.isEmpty(maskRequired))
                continue;

            field = setAccessible(field);
            MaskingInfo maskingInfo = maskRequired.MASKING_INFO();
            String fieldString = (String) field.get(obj);

            if(fieldString.length() < maskingInfo.getMinLength())
                    return obj;

            int endPostion = fieldString.length() - maskingInfo.getRightEndPosition();

            fieldString = maskString(fieldString, maskingInfo.getLeftStartPosition(), endPostion
                                , maskingInfo.getMaksingChar());


            field.set(obj, fieldString);

        }

        return obj;
    }

    public static Field setAccessible(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

        return field;
    }


    private static String maskString(String str, int start, int end, boolean fromRight, char maskChar) throws Exception {

        if(fromRight) {
            int newStart = str.length() - end;
            end = str.length() - start;

            return maskString(str, newStart, end, maskChar);
        }


        int newStart = 0;
        int newEnd = start;
        str = maskString(str, newStart, newEnd, maskChar);

        newStart = end;
        newEnd = str.length();
        str = maskString(str, newStart, newEnd, maskChar);

        return maskString(str, start, end, maskChar);

    }


    private static String maskString(String str, int start, int end, char maskChar) throws Exception {

        log.debug("before masking str : {}", str);

        if (StringUtils.isEmpty(str))
            return "";

        if (start < 0)
            start = 0;

        if (end > str.length())
            end = str.length();

        if (start > end)
            throw new Exception("End index cannot be greater than start index");

        int maskLength = end - start;

        if (maskLength == 0)
            return str;

        StringBuilder sbMaskString = new StringBuilder(maskLength);

        for (int i = 0; i < maskLength; i++) {
            sbMaskString.append(maskChar);
        }

        String makingStr = str.substring(0, start)
                + sbMaskString.toString()
                + str.substring(start + maskLength);

        log.debug("makingStr : {}", makingStr);

        return makingStr;
    }

    public static Integer getVat(Integer vat, Integer amount) {

        return ObjectUtils.isEmpty(vat) ? Math.round(amount / 11) : vat;
    }

    public static Integer getVat(Integer amount) {

        return Math.round(amount / 11);
    }


}
