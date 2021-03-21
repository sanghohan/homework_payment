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

import java.util.ArrayList;
import java.util.List;



@Slf4j
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public static List<String> CARD_DATA = new ArrayList<>();

    @Transactional
    public PayResVo pay(PayReqDto reqDto) throws Exception {

        checkDuplicatedCardData(reqDto.getCardData());
        paymentRepository.save(Payment.get(reqDto));
        removeCardData(reqDto.getCardData());

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

    private static void checkDuplicatedCardData(String cardData) throws Exception{

        synchronized(CARD_DATA) {

            if (CARD_DATA.contains(cardData))
                throw new Exception("동시에 같은 카드로 결제 할수 없습니다.");

            CARD_DATA.add(cardData);
        }

    }
    private static void removeCardData(String cardData) {

        synchronized(CARD_DATA) {
            CARD_DATA.remove(cardData);
        }

    }


}