package com.example.paymenthistoryservice.repository;

import com.example.paymenthistoryservice.entity.PaymentHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {

    Page<PaymentHistory> findAllByUserId(String userId, PageRequest pageRequest);

    @Query("select receipt from PaymentHistory receipt where receipt.senderRequestId=:senderRequestId")
    PaymentHistory findSenderRequestId(String senderRequestId);

}
