package com.kakopay.homework.payment.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = { "id", "payId" } )
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String payId;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    @Column(nullable = false)
    private Long payAmount;

    @Column(nullable = true)
    private Long cancelAmount;

    @Column(nullable = false)
    private Integer installmentMonths;

    @Column
    private Long payVat;

    @Column
    private Long cancelVat;

    @Column(nullable = false)
    private String cardData;

    @Column(length = 450)
    private String LinkedData;

    @Enumerated(EnumType.STRING)
    private PayStatus status;

    @Column(nullable = false)
    private LocalDateTime regDt;

    @Column(nullable = false)
    private LocalDateTime updDt;

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
    public Payment(String payId, Long payAmount, Long payVat, Integer installmentMonths, String cardData) {

        Assert.hasLength(payId, "payId must not be empty");
        Assert.state(payAmount > 100, "payAmount must not be bigger than 100");
        Assert.hasLength(cardData, "cardData must not be empty");

        this.payId = payId;
        this.payAmount = payAmount;
        this.cancelAmount = 0L;
        this.installmentMonths = installmentMonths;
        this.payVat =  payVat > 0 ? payVat : Long.valueOf(Math.round(payAmount/11));
        this.payType = PayType.PAY;
        this.status = PayStatus.PAID;
        this.cardData = cardData;
        this.regDt = LocalDateTime.now();
        this.updDt = LocalDateTime.now();
    }

    @Builder(builderClassName = "CancelBuilder", builderMethodName = "cancelBuilder", buildMethodName = "cancelBuild")
    public Payment(String payId, Long cancelAmount, Long cancelVat, String cardData) {

        Assert.hasLength(payId, "payId must not be empty");
        Assert.state(cancelAmount == 0, "cancelAmount must not be bigger than 0");
        Assert.hasLength(cardData, "cardData must not be empty");

        this.payId = payId;
        this.cancelAmount = cancelAmount;
        this.payAmount = 0l;
        this.installmentMonths = 0;
        this.cancelVat = cancelVat > 0 ? cancelVat : Long.valueOf(Math.round(cancelAmount/11));
        this.payType = PayType.CANCEL;
        this.cardData = cardData;
        this.regDt = LocalDateTime.now();
        this.updDt = LocalDateTime.now();
    }


}
