package com.kakopay.homework.payment.entity;

import com.kakopay.homework.payment.Exception.PayException;
import com.kakopay.homework.payment.controller.vo.CardDataVo;
import com.kakopay.homework.payment.dto.CancelDto;
import com.kakopay.homework.payment.dto.PayDto;
import com.kakopay.homework.payment.util.PayDataUtil;
import lombok.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.kakopay.homework.payment.util.PayDataUtil.getVat;

@Entity
@Table(indexes = {@Index(name = "idx_payment", columnList = "txId")})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"txId"})
public class Payment {

    @Id
    @Column(nullable = false, length = 20)
    private String txId;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    @Column(nullable = false)
    private String cardData;

    @Column(nullable = false)
    private String installmentMonths;

    @Column(nullable = false)
    private Integer payAmount;

    @Column
    private Integer payVat;

    @Column
    @Version
    private Integer cancelAmount;

    @Column
    private Integer cancelVat;

    @Column(length = 500)
    private String linkedData;

    @Enumerated(EnumType.STRING)
    private PayStatus status;

    @Column
    private String orgPayTxId;

    @Column(nullable = false)
    private LocalDateTime regDt;

    @Column(nullable = false)
    private LocalDateTime updDt;

    @Column
    @Version
    private int version;

    public enum PayStatus {
        PAID,
        CANCELD,
        PARTIALLY_CANCELLED
    }

    public enum PayType {
        PAY,
        CANCEL
    }


    @Builder(builderClassName = "PayBuilder", builderMethodName = "payBuilder", buildMethodName = "payBuild")
    public Payment(String txId, Integer payAmount, Integer payVat, Integer installmentMonths, String cardData
            , String linkedData) {

        Assert.hasLength(txId, "txId must not be empty");
        Assert.state(payAmount > 100, "payAmount must not be bigger than 100");
        Assert.hasLength(cardData, "cardData must not be empty");

        this.txId = txId;
        this.payAmount = payAmount;
        this.cancelAmount = 0;
        this.cancelVat = 0;
        this.installmentMonths = String.valueOf(installmentMonths);
        this.payVat = getVat(payVat, payAmount);
        this.payType = PayType.PAY;
        this.status = PayStatus.PAID;
        this.cardData = cardData;
        this.linkedData = linkedData;
        this.regDt = LocalDateTime.now();
        this.updDt = LocalDateTime.now();
    }

    @Builder(builderClassName = "CancelBuilder", builderMethodName = "cancelBuilder", buildMethodName = "cancelBuild")
    public Payment(String txId, Integer cancelAmount, Integer cancelVat, String cardData, String linkedData,
                   String orgPayTxId) {

        Assert.hasLength(txId, "txId must not be empty");
        Assert.state(cancelAmount > 0, "cancelAmount must not be bigger than 0");
        Assert.hasLength(cardData, "cardData must not be empty");

        this.txId = txId;
        this.cancelAmount = cancelAmount;
        this.payAmount = 0;
        this.installmentMonths = "00";
        this.cancelVat = getVat(cancelVat, cancelAmount);
        this.payType = PayType.CANCEL;
        this.cardData = cardData;
        this.linkedData = linkedData;
        this.orgPayTxId = orgPayTxId;
        this.regDt = LocalDateTime.now();
        this.updDt = LocalDateTime.now();
    }

    public static Payment getPay(PayDto reqDto, String linkedData) throws Exception {

        return Payment.payBuilder()
                .txId(reqDto.getTxId())
                .payAmount(reqDto.getPayAmount())
                .payVat(reqDto.getPayVat())
                .installmentMonths(reqDto.getInstallmentMonths())
                .cardData(PayDataUtil.getEncCardData(reqDto.getCardData()))
                .linkedData(linkedData)
                .payBuild();
    }

    public static Payment getCancel(CancelDto cancelDto, String cardData, String linkedData, String orgPayTxId) throws Exception {

        return Payment.cancelBuilder()
                .txId(cancelDto.getTxId())
                .cancelAmount(cancelDto.getCancelAmount())
                .cancelVat(cancelDto.getCalculatedVat())
                .cardData(PayDataUtil.getEncCardData(cardData))
                .linkedData(linkedData)
                .orgPayTxId(orgPayTxId)
                .cancelBuild();
    }

    public void cancel(CancelDto cancelDto) throws Exception {

        checkCancelPolicy(cancelDto);
        cancelAmount += cancelDto.getCancelAmount();
        cancelVat += cancelDto.getCalculatedVat();

        if (cancelAmount.equals(payAmount)) {
            status = PayStatus.CANCELD;
        } else {
            status = PayStatus.PARTIALLY_CANCELLED;
        }

        updDt = LocalDateTime.now();

    }

    private void checkCancelPolicy(CancelDto cancelDto) throws Exception {

        if (cancelDto.getCancelAmount() > getRemainAmount())
            throw new PayException("PAY_3003", String.valueOf(cancelDto.getCancelAmount()), String.valueOf(getRemainAmount()));

        if (cancelDto.getCancelAmount().equals(getRemainAmount())) {
            if(ObjectUtils.isEmpty(cancelDto.getReqCancelVat()) && cancelDto.getCalculatedVat() > getRemainingVat()) {
                cancelDto.setCalculatedVat(getRemainingVat());
            }

            if(ObjectUtils.isNotEmpty(cancelDto.getReqCancelVat()) && cancelDto.getReqCancelVat() > getRemainingVat())
                throw new PayException("PAY_3004", String.valueOf(getRemainingVat()));

            if(ObjectUtils.isNotEmpty(cancelDto.getReqCancelVat()) && cancelDto.getReqCancelVat() < getRemainingVat())
                throw new PayException("PAY_3005", String.valueOf(getRemainingVat()));

            return;
        }

        if (cancelDto.getCalculatedVat() > getRemainingVat())
            throw new PayException("PAY_3004", String.valueOf(cancelDto.getCalculatedVat()), String.valueOf(getRemainingVat()));
    }


    public int getRemainAmount() {
        return payAmount - cancelAmount;
    }

    public int getRemainingVat() {
        return this.payVat - this.cancelVat;
    }

    public CardDataVo getCardData() throws Exception {

        return PayDataUtil.getDecCardData(cardData);
    }
}
