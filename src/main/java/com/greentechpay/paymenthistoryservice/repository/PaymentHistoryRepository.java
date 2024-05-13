package com.greentechpay.paymenthistoryservice.repository;

import com.greentechpay.paymenthistoryservice.entity.PaymentHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long>, JpaSpecificationExecutor<PaymentHistory> {

    Page<PaymentHistory> findAllBy(PageRequest pageRequest);

    Page<PaymentHistory> findAllByUserId(String userId, PageRequest pageRequest);

    @Query("select receipt from PaymentHistory receipt where receipt.senderRequestId=:senderRequestId")
    PaymentHistory findSenderRequestId(String senderRequestId);

    PaymentHistory findByTransactionId(String id);

    boolean existsByUserId(String id);

    boolean existsBySenderRequestId(String id);

    boolean existsByTransactionId(String id);
}
