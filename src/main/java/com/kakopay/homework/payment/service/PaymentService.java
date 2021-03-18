package com.kakopay.homework.payment.service;

import com.kakopay.homework.payment.dto.PayResDto;
import com.kakopay.homework.payment.dto.PayDto;
import com.kakopay.homework.payment.entity.PaymentData;
import com.kakopay.homework.payment.entity.Refund;
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
    public PayResDto pay(PayDto reqDto) {


       Refund refund = Refund.ByCancelBuilder()
                        .payId("test")
                        .payAmount(10)
                        .installmentMonths(0)
                        .cardData("test11")
                        .build();

      /*Refund refund2 = Refund.ByAccountBuilder()
                            .cancelAmount(100)
                            .cardData("test")
                            .build();

       PaymentData paymentData = PaymentData.ByPayBuilder()
               .payId("test")
               .payAmount(10)
               .installmentMonths(0)
               .cardData("test11")
               .build();*/

        return new PayResDto();
    }


}