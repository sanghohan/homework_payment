package com.kakopay.homework.payment.external.linkdata.stringdata;

import lombok.Data;

@Data
public class StringData {

    private Header header;
    private Body body;

    public String toStringData() {

        return header.getStringData() + body.getStringData();
    }
}
