package com.greentechpay.paymenthistoryservice.repository;

import com.greentechpay.paymenthistoryservice.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long>,
        JpaSpecificationExecutor<PaymentHistory> {

    List<PaymentHistory> findAllByDate(LocalDate date);


    @Query("select receipt from PaymentHistory receipt where receipt.id=:id and " +
            " (receipt.merchantId=:merchantId or :merchantId is null)")
    Optional<PaymentHistory> findById(Long id, Long merchantId);

    @Query("select ph from PaymentHistory  ph where ph.transactionId=:id")
    PaymentHistory findByTransactionId(String id);

    Boolean existsByTransactionId(@Param("transactionId") String transactionId);
}
