package com.kakopay.homework.payment.util;

import net.bytebuddy.utility.RandomString;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class PayIdUtil {

    public static String getPayId() {

        return String.valueOf(System.currentTimeMillis()) + RandomString.make(7);

    }
}
