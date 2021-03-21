package com.kakopay.homework.payment.external.linkdata.stringdata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedLengthField {

    Alignment align() default Alignment.RIGHT;
    char padChar() default ' ';
    int maxSize() default 0;

    enum Alignment {
        LEFT,
        RIGHT,
        ;
    }
}



