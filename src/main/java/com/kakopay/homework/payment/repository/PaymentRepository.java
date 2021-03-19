package com.kakopay.homework.payment.repository;


import com.kakopay.homework.payment.entity.Payment;

import java.util.List;

public interface PaymentRepository {

    List<Payment> findPaymentsByPayId(String payid);
    void save(Payment payment);
}