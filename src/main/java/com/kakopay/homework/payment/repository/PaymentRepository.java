package com.kakopay.homework.payment.repository;


import com.kakopay.homework.payment.entity.PaymentData;

import java.util.List;

public interface PaymentRepository {

    List<PaymentData> findPaymentsByPayId(String payid);
    void save(PaymentData paymentData);
}