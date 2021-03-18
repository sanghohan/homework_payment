package com.kakopay.homework.payment.entity;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENT_DATA")
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode
public class PaymentData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String payId;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    @Column(nullable = false)
    private Integer payAmount;

    @Column(nullable = true)
    private Integer cancelAmount;

    @Column(nullable = false)
    private Integer installmentMonths;

    @Column(nullable = true)
    private Integer payVat;

    @Column(nullable = true)
    private Integer cancelVat;

    @Column(nullable = false)
    private String cardData;

    @Column(nullable = false, length = 450)
    private String LinkedData;

    @Enumerated(EnumType.STRING)
    private PayStatus status;

    @Column(nullable = false)
    private LocalDateTime regDt;

    @Column(nullable = false)
    private LocalDateTime updDt;

    @Builder(builderClassName = "ByPayBuilder", buildMethodName = "ByPayBuilder")
    public PaymentData(Integer payAmount, Integer installmentMonths, String cardData) {

        //Assert.state(payAmount == 0, "payAmount must not be bigger than 0");
        //Assert.hasText(cardData, "cardData must not be empty");

        this.payAmount = payAmount;
        this.cancelAmount = 0;
        this.installmentMonths = installmentMonths;
        this.payVat = Math.round(payAmount/11);
        this.payType = PayType.PAY;
        this.cardData = cardData;
        this.regDt = LocalDateTime.now();
        this.updDt = LocalDateTime.now();
    }

    /*@Builder(builderClassName = "ByCancelBuilder", buildMethodName = "ByCancelBuilder")
    public PaymentData(String payId, Integer cancelAmount, String cardData) {

        Assert.hasText(payId, "payId must not be empty");
        Assert.state(cancelAmount == 0, "cancelAmount must not be bigger than 0");
        Assert.hasText(cardData, "cardData must not be empty");

        this.payId = payId;
        this.cancelAmount = cancelAmount;
        this.payAmount = 0;
        this.installmentMonths = 0;
        this.cancelVat = Math.round(cancelAmount/11);
        this.payType = PayType.CANCEL;
        this.cardData = cardData;
        this.updDt = LocalDateTime.now();
    }*/


}
