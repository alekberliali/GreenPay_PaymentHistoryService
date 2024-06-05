package com.greentechpay.paymenthistoryservice.repository;

import com.greentechpay.paymenthistoryservice.entity.PaymentHistory;
import com.greentechpay.paymenthistoryservice.service.specification.PaymentHistorySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long>,
        JpaSpecificationExecutor<PaymentHistory> {

    Page<PaymentHistory> findAllBy(PageRequest pageRequest, Specification<PaymentHistorySpecification> specification);

    @Query("select receipt from PaymentHistory receipt where receipt.senderRequestId=:senderRequestId")
    PaymentHistory findSenderRequestId(String senderRequestId);

    @Query("select receipt from PaymentHistory receipt where receipt.id=:id")
    Optional<PaymentHistory> findById(Long id);

    PaymentHistory findByTransactionId(String id);

    boolean existsByUserId(String id);
}
