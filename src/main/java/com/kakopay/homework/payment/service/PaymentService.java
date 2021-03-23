package com.kakopay.homework.payment.service;

import com.kakopay.homework.payment.Exception.PayException;
import com.kakopay.homework.payment.controller.vo.StringData;
import com.kakopay.homework.payment.dto.CancelDto;
import com.kakopay.homework.payment.dto.PayDto;
import com.kakopay.homework.payment.entity.Payment;
import com.kakopay.homework.payment.external.linkdata.stringdata.Body;
import com.kakopay.homework.payment.external.linkdata.stringdata.Header;
import com.kakopay.homework.payment.repository.PaymentRepository;
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

    private static Queue<String> payCardDATAqueue = new ConcurrentLinkedQueue<>();
    private static Queue<String> cancelTxIdqueue = new ConcurrentLinkedQueue<>();

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

    private void validateCancelRequest(Payment payment, CancelDto reqDto) throws Exception {

        if (ObjectUtils.isEmpty(payment))
            throw new PayException("PAY_3001", reqDto.getOrgPayTxId());

        if (Payment.PayStatus.CANCELD.equals(payment.getStatus()))
            throw new PayException("PAY_3002", reqDto.getOrgPayTxId());
    }

    private StringData insertCancelData(Payment payment, CancelDto reqDto) throws Exception {

        StringData data = new StringData(reqDto.getTxId(),
                Header.getCancel((reqDto)), Body.getCancel(reqDto, payment.getCardData()));

        String cardData = PayDataUtil.getCardData(payment.getCardData());

        paymentRepository.save(Payment.getCancel(reqDto, cardData,
                data.getStringData(), reqDto.getOrgPayTxId()));

        return data;

    }

    private static void checkDuplicatedCardData(String cardData) throws Exception {

        if (payCardDATAqueue.contains(cardData))
            throw new PayException("PAY_4001");

        payCardDATAqueue.add(cardData);


    }

    private static void removeCardData(String cardData) {
        payCardDATAqueue.remove(cardData);
    }

    private static void checkDuplicatedCancelRequest(String txId) throws Exception {

        if (cancelTxIdqueue.contains(txId))
            throw new PayException("PAY_4002", txId);

        cancelTxIdqueue.add(txId);


    }

    private static void removeCancelRequest(String txId) {
        cancelTxIdqueue.remove(txId);
    }
}