package com.kakopay.homework.payment.external.linkdata.stringdata;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StringData {

    private Header header;
    private Body body;

    public String toString() {

        return header.getStringData() + body.getStringData();
    }
}
