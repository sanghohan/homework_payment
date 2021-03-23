package com.kakopay.homework.payment.util;

import com.kakopay.homework.payment.Exception.PayException;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AES128Cipher {

    final static String secretKey = "kakao12345aaaaaa";
    final static String IV = secretKey.substring(0, 16);

    //암호화
    public static String AES_Encode(String str) throws Exception {
        try {
            byte[] keyData = secretKey.getBytes(StandardCharsets.UTF_8);
            SecretKey secureKey = new SecretKeySpec(keyData, "AES");
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));

            byte[] encrypted = c.doFinal(str.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.encodeBase64(encrypted));

        } catch (Exception e) {
            throw new PayException("PAY_8000");
        }

    }

    //복호화
    public static String AES_Decode(String str) throws Exception {
        try {
            byte[] keyData = secretKey.getBytes();
            SecretKey secureKey = new SecretKeySpec(keyData, "AES");
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));

            byte[] byteStr = Base64.decodeBase64(str.getBytes());

            return new String(c.doFinal(byteStr), StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new PayException("PAY_8000");
        }
    }
}