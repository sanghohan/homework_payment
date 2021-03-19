package com.kakopay.homework.payment.repository;


import com.kakopay.homework.payment.entity.Payment;
import com.kakopay.homework.payment.entity.QPayment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PaymentRepositoryImpl extends QuerydslRepositorySupport implements PaymentRepository {

    private static final QPayment $ = QPayment.payment;

    @Autowired
    private EntityManager em;

    @Autowired
    private JPAQueryFactory queryFactory;

    public PaymentRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Payment.class);
        this.queryFactory = queryFactory;

    }

    @Override
    public List<Payment> findPaymentsByPayId(String payid) {
        return null;
    }

    @Override
    public void save(Payment payment) {
        em.persist(payment);
    }
}
