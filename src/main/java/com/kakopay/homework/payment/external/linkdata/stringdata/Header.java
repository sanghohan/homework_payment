package com.kakopay.homework.payment.external.linkdata.stringdata;


import com.kakopay.homework.payment.dto.CancelDto;
import com.kakopay.homework.payment.dto.PayDto;
import com.kakopay.homework.payment.entity.Payment;
import lombok.Builder;
import lombok.Getter;

@FixedLengthType(length = 34)
@Getter
@Builder
public class Header {

    @FixedLengthField(fieldSet = FieldSet.NUMBER, length = 4)
    @Builder.Default
    private int length = 416;

    @FixedLengthField(fieldSet = FieldSet.STRING, length = 10)
    private Payment.PayType payType;

    @FixedLengthField(fieldSet = FieldSet.STRING, length = 20)
    private String txId;

    public String getStringData() {
        return StringDataGenerator.getStringData(this);
    }

    public static Header getPay(PayDto payDto) {

        return Header.builder()
                .payType(Payment.PayType.PAY)
                .txId(payDto.getTxId())
                .build();
    }

    public static Header getCancel(CancelDto cancelDto) {

        return Header.builder()
                .payType(Payment.PayType.CANCEL)
                .txId(cancelDto.getTxId())
                .build();
    }
}
