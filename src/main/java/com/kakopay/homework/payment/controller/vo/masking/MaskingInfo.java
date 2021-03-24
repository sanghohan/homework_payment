package com.kakopay.homework.payment.controller.vo.masking;

import lombok.Getter;

@Getter
public enum MaskingInfo {

    CARD_MASKING(6, 3,6, '*'),
    ;

    private int leftStartPosition;
    private int rightEndPosition;
    private int minLength;
    private char maksingChar;

    MaskingInfo(int leftStartPosition, int rightEndPosition, int minLength, char maksingChar) {

        this.leftStartPosition = leftStartPosition;
        this.rightEndPosition = rightEndPosition;
        this.minLength = minLength;
        this.maksingChar = maksingChar;
    }
}
