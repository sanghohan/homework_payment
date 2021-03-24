package com.kakopay.homework.payment.controller.vo.res;

import com.kakopay.homework.payment.controller.vo.masking.MaskingInfo;
import com.kakopay.homework.payment.controller.vo.masking.MaskRequired;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class CardDataVo {

    @MaskRequired(MASKING_INFO = MaskingInfo.CARD_MASKING)
    private String cardNum;
    private String validPeriod;
    private String cvc;
}
