package com.kakopay.homework.payment.external.linkdata.stringdata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class StringData {

    private String payId;
    private String stringData;

    @JsonIgnore
    private Header header;

    @JsonIgnore
    private Body body;

    public StringData(String payId, Header header, Body body) {
        this.payId = payId;
        this.header = header;
        this.body = body;
        this.stringData = toString();
    }

    public String toString() {

        return header.getStringData() + body.getStringData();
    }
}
