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

    @Query("select receipt from PaymentHistory receipt where receipt.senderRequestId=:senderRequestId")
    PaymentHistory findSenderRequestId(String senderRequestId);

    @Query("select receipt from PaymentHistory receipt where receipt.id=:id")
    Optional<PaymentHistory> findById(Long id);

    @Query("select ph from PaymentHistory  ph where ph.transactionId=:id")
    PaymentHistory findByTransactionId(String id);

    @Query("select p.serviceId, sum (p.amount)from PaymentHistory p where (:startDate is null  or p.date>=:startDate)" +
            "and (:endDate is null or p.date<=:endDate) group by p.serviceId")
    List<Object[]> findServiceStatistics(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    default Map<Integer, BigDecimal> getServiceStatistics(LocalDate startDate, LocalDate endDate){
        return findServiceStatistics(startDate,endDate).stream()
                .collect(Collectors.toMap(
                        entry->(Integer) entry[0],
                        entry->(BigDecimal) entry[1]
                ));
    }
}
