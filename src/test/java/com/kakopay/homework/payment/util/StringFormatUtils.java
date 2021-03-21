package com.kakopay.homework.payment.util;

import java.util.Arrays;


public class StringFormatUtils {

    public static String padByStringLength(String source, char fill, int stringLength, boolean right) {
        if (source.length() > stringLength) return source;

        char[] out = new char[stringLength];
        if (right) {
            int sourceOffset = stringLength - source.length();
            System.arraycopy(source.toCharArray(), 0, out, sourceOffset, source.length());
            Arrays.fill(out, 0, sourceOffset, fill);
        } else {
            System.arraycopy(source.toCharArray(), 0, out, 0, source.length());
            Arrays.fill(out, source.length(), stringLength, fill);
        }
        return new String(out);
    }

}
