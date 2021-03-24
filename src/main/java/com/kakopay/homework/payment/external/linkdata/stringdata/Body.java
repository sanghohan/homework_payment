package com.kakopay.homework.payment.external.linkdata.stringdata;

import com.kakopay.homework.payment.dto.CancelDto;
import com.kakopay.homework.payment.dto.CardDataDto;
import com.kakopay.homework.payment.dto.PayDto;
import com.kakopay.homework.payment.runtime.Exception.PayException;
import com.kakopay.homework.payment.util.PayDataUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@FixedLengthType(length = 416)
@Builder
@Slf4j
public class Body {

    @FixedLengthField(fieldSet = FieldSet.NUMBER_L, length = 20)
    private String cardNum;

    @FixedLengthField(fieldSet = FieldSet.NUMBER_0, length = 2)
    private int installmentMonths;

    @FixedLengthField(fieldSet = FieldSet.NUMBER_L, length = 4)
    private String validPeriod;

    @FixedLengthField(fieldSet = FieldSet.NUMBER_L, length = 3)
    private String cvc;

    @FixedLengthField(fieldSet = FieldSet.NUMBER, length = 10)
    private int transactionAmount;

    @FixedLengthField(fieldSet = FieldSet.NUMBER_0, length = 10)
    private int vat;

    @FixedLengthField(fieldSet = FieldSet.STRING, length = 20)
    @Builder.Default
    private String orgPayId = " ";

    @FixedLengthField(fieldSet = FieldSet.STRING, length = 300)
    private String encryptedCardData;

    @FixedLengthField(fieldSet = FieldSet.RESERVED, length = 47)
    @Builder.Default
    private String reserved = " ";

    public String getStringData() {
        return StringDataGenerator.getStringData(this);
    }

    public static BodyBuilder builder() {
        return new encryptedCardDataBuilder();
    }

    private static class encryptedCardDataBuilder extends BodyBuilder {

        @Override
        public Body build() {

            try {
                String encryptedCardData = PayDataUtil.getEncCardStringData(super.cardNum, super.validPeriod, super.cvc);
                super.encryptedCardData(encryptedCardData);

                if(super.vat == 0 )
                    super.vat(PayDataUtil.getVat(super.vat, super.transactionAmount));

            } catch (Exception e) {
                log.error(e.getMessage());
                return super.build();
            }

            return super.build();
        }
    }

    public static Body getPay(PayDto payDto) throws Exception {

        CardDataDto cardDataDto = PayDataUtil.getCardDataObj(payDto.getCardData());
        return Body.builder()
                .cardNum(cardDataDto.getCardNum())
                .validPeriod(cardDataDto.getValidPeriod())
                .cvc(cardDataDto.getCvc())
                .installmentMonths(payDto.getInstallmentMonths())
                .transactionAmount(payDto.getPayAmount())
                .vat(PayDataUtil.getVat(payDto.getPayVat(), payDto.getPayAmount()))
                .build();

    }

    public static Body getCancel(CancelDto cancelDto, CardDataDto cardDataVo) {

        return Body.builder()
                .cardNum(cardDataVo.getCardNum())
                .validPeriod(cardDataVo.getValidPeriod())
                .cvc(cardDataVo.getCvc())
                .installmentMonths(0)
                .transactionAmount(cancelDto.getCancelAmount())
                .vat(cancelDto.getCalculatedVat())
                .build();

    }


}
