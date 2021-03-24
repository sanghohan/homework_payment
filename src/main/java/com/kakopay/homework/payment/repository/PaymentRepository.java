package com.kakopay.homework.payment.repository;


import com.kakopay.homework.payment.entity.Payment;

public interface PaymentRepository {

    Payment findPaymentsByTxId(String txId);
    void save(Payment payment);
    Payment findCancellablePaymentByTxId(String txId);

}