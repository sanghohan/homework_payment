package com.kakopay.homework.payment.external.linkdata.stringdata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedLengthType {
    
    enum LengthType {
        STRING,
        BYTE
    } 
    
    LengthType lengthType() default LengthType.STRING;
    String charset() default "UTF-8";
    int length();
}
