package com.kakopay.homework.payment.service;

import com.kakopay.homework.payment.controller.vo.PayResVo;
import com.kakopay.homework.payment.dto.CancelDto;
import com.kakopay.homework.payment.dto.PayReqDto;
import com.kakopay.homework.payment.entity.Payment;
import com.kakopay.homework.payment.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public PayResVo pay(PayReqDto reqDto) {

       Payment payment = Payment.payBuilder()
               .payId("test")
               .payAmount(100)
               .installmentMonths(0)
               .cardData("test11")
               .payBuild();

        paymentRepository.save(payment);

        return null;

    }

    @Transactional
    public CancelDto cancel(CancelDto reqDto) {

        Payment payment = Payment.cancelBuilder()
                .cancelAmount(100)
                .cardData("test")
                .cancelBuild();

        return new CancelDto();
    }


}