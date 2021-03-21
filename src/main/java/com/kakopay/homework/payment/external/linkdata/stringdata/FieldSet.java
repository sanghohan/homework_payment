package com.kakopay.homework.payment.external.linkdata.stringdata;

import lombok.Getter;

@Getter
public enum FieldSet {

    NUMBER(FieldAlignment.RIGHT, ' '),
    NUMBER_0(FieldAlignment.RIGHT, '0'),
    NUMBER_L(FieldAlignment.LEFT, ' '),
    STRING(FieldAlignment.LEFT, ' '),
    RESERVED(FieldAlignment.LEFT, ' ');

    private FieldAlignment alignment;
    private char padChar;

    FieldSet(FieldAlignment alignment, char padChar) {

        this.alignment = alignment;
        this.padChar = padChar;
    }
}
