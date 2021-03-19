package com.kakopay.homework.payment.util;

import net.bytebuddy.utility.RandomString;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.StringJoiner;
import java.util.UUID;

public class PayDataUtil {

    public static String getPayId() {

        return String.valueOf(System.currentTimeMillis()) + RandomString.make(7);

    }

    public static String getCardData(String cardNum, String validPeriod, String cvc) {

        StringJoiner stringJoiner = new StringJoiner("|");

        return stringJoiner
                .add(cardNum)
                .add(validPeriod)
                .add(cvc).toString();

    }

}
