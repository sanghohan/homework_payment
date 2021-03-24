package com.kakopay.homework.payment.service;

import com.kakopay.homework.payment.controller.vo.res.PayAmountInfo;
import com.kakopay.homework.payment.controller.vo.res.PayInfoResVo;
import com.kakopay.homework.payment.controller.vo.res.StringData;
import com.kakopay.homework.payment.dto.CancelDto;
import com.kakopay.homework.payment.dto.PayDto;
import com.kakopay.homework.payment.entity.Payment;
import com.kakopay.homework.payment.external.linkdata.stringdata.Body;
import com.kakopay.homework.payment.external.linkdata.stringdata.Header;
import com.kakopay.homework.payment.repository.PaymentRepository;
import com.kakopay.homework.payment.runtime.Exception.PayException;
import com.kakopay.homework.payment.util.PayDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


@Slf4j
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EntityManager em;

    private static Queue<String> PAYING_CARD_DATA = new ConcurrentLinkedQueue<>();
    private static Queue<String> CANCELING_TXID_DATA = new ConcurrentLinkedQueue<>();

    @Transactional
    public StringData pay(PayDto reqDto) throws Exception {

        try {
            checkDuplicatedCardData(reqDto.getCardData());
            StringData data = new StringData(reqDto.getTxId(), Header.getPay((reqDto)), Body.getPay(reqDto));
            paymentRepository.save(Payment.getPay(reqDto, data.getStringData()));
            return data;

        } finally {
            removeCardData(reqDto.getCardData());
        }
    }

    @Transactional
    public StringData cancel(CancelDto reqDto) throws Exception {

        try {
            checkDuplicatedCancelRequest(reqDto.getOrgPayTxId());

            Payment payment = paymentRepository.findCancellablePaymentByTxId(reqDto.getOrgPayTxId());

            validateCancelRequest(payment, reqDto);
            payment.cancel(reqDto);
            //em.flush();
            return insertCancelData(payment, reqDto);

        } finally {
            removeCancelRequest(reqDto.getOrgPayTxId());
        }


    }

    @Transactional
    public PayInfoResVo findPayInfo(String txId) throws Exception {

        Payment payment = paymentRepository.findPaymentsByTxId(txId);

        if (ObjectUtils.isEmpty(payment))
            throw new PayException("PAY_3001", txId);

        PayAmountInfo payAmountInfo = PayAmountInfo.builder()
                .payment(payment)
                .build();

        return PayInfoResVo.builder()
                .txId(payment.getTxId())
                .payType(payment.getPayType())
                .status(payment.getStatus())
                .payAmountInfo(payAmountInfo)
                .cardDataVo(PayDataUtil.getMaskingCardDataObj(payment.getCardData()))
                .orgTxId(payment.getOrgPayTxId())
                .transactionDt(payment.getUpdDt())
                .build();

    }

    private void validateCancelRequest(Payment payment, CancelDto reqDto) throws Exception {

        if (ObjectUtils.isEmpty(payment))
            throw new PayException("PAY_3001", reqDto.getOrgPayTxId());

        if (Payment.PayStatus.CANCELED.equals(payment.getStatus()))
            throw new PayException("PAY_3002", reqDto.getOrgPayTxId());
    }

    private StringData insertCancelData(Payment payment, CancelDto reqDto) throws Exception {

        StringData data = new StringData(reqDto.getTxId(),
                Header.getCancel((reqDto)), Body.getCancel(reqDto, payment.getCardData()));

        String cardData = PayDataUtil.getCardStringData(payment.getCardData());

        paymentRepository.save(Payment.getCancel(reqDto, cardData,
                data.getStringData(), reqDto.getOrgPayTxId()));

        return data;

    }

    private static void checkDuplicatedCardData(String cardData) throws Exception {

        if (PAYING_CARD_DATA.contains(cardData))
            throw new PayException("PAY_4001");

        PAYING_CARD_DATA.add(cardData);


    }

    private static void removeCardData(String cardData) {
        PAYING_CARD_DATA.remove(cardData);
    }

    private static void checkDuplicatedCancelRequest(String txId) throws Exception {

        if (CANCELING_TXID_DATA.contains(txId))
            throw new PayException("PAY_4002", txId);

        CANCELING_TXID_DATA.add(txId);


    }

    private static void removeCancelRequest(String txId) {
        CANCELING_TXID_DATA.remove(txId);
    }
}