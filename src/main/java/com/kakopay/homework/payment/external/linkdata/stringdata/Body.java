package com.kakopay.homework.payment.external.linkdata.stringdata;

import lombok.Getter;

@Getter
public class Body {

    @FixedLengthField(align = FixedLengthField.Alignment.RIGHT, maxSize = 20)
    private String cardNum;
    private String validPeriod;
    private String cvc;
    private Integer transactionAmount;
    private Integer vat;
    private String orgPayId;

    private String encryptedCardData;
    private String reserved;

}
