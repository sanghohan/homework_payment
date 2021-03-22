package com.kakopay.homework.payment.repository;


import com.kakopay.homework.payment.entity.Payment;

import java.util.List;

public interface PaymentRepository {

    List<Payment> findPaymentsByTxId(String txId);
    void save(Payment payment);
    Payment findCancellablePaymentByTxId(String txId);
}