package com.example.paymenthistoryservice.repository;

import com.example.paymenthistoryservice.entity.PaymentHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, String> {

    Page<PaymentHistory> findAllByUserId(String userId, PageRequest pageRequest);


}
