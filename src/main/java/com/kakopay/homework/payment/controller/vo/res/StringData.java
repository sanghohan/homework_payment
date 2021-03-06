package com.kakopay.homework.payment.controller.vo.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kakopay.homework.payment.external.linkdata.stringdata.Body;
import com.kakopay.homework.payment.external.linkdata.stringdata.Header;
import com.kakopay.homework.payment.runtime.Exception.PayException;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class StringData {

    private String txId;
    private String stringData;

    @JsonIgnore
    private Header header;

    @JsonIgnore
    private Body body;

    public StringData(String txId, Header header, Body body) {
        this.txId = txId;
        this.header = header;
        this.body = body;
        this.stringData = getStringData();
    }

    public String getStringData() {
        return header.getStringData() + body.getStringData();
    }
}
