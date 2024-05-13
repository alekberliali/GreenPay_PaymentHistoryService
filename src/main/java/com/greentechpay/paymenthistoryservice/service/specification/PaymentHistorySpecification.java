package com.greentechpay.paymenthistoryservice.service.specification;

import com.greentechpay.paymenthistoryservice.dto.Currency;
import com.greentechpay.paymenthistoryservice.dto.PaymentHistoryCriteria;
import com.greentechpay.paymenthistoryservice.dto.Status;
import com.greentechpay.paymenthistoryservice.dto.TransferType;
import com.greentechpay.paymenthistoryservice.entity.PaymentHistory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PaymentHistorySpecification implements Specification<PaymentHistory> {

    private final PaymentHistoryCriteria paymentHistoryCriteria;

    @Override
    public Predicate toPredicate(Root<PaymentHistory> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (paymentHistoryCriteria.getUserId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("userId"), paymentHistoryCriteria.getUserId()));
        }
        if (paymentHistoryCriteria.getTransactionId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("transactionId"), paymentHistoryCriteria.getTransactionId()));
        }
        if (paymentHistoryCriteria.getStartDate() != null && paymentHistoryCriteria.getEndDate() != null) {
            predicates.add(criteriaBuilder.between(root.get("date"), paymentHistoryCriteria.getStartDate(),
                    paymentHistoryCriteria.getEndDate()));
        }
        if (paymentHistoryCriteria.getCurrencies() != null) {
            for (Currency currency : paymentHistoryCriteria.getCurrencies()) {
                predicates.add(criteriaBuilder.equal(root.get("currency"), currency));
            }
        }
        if (paymentHistoryCriteria.getTypes() != null) {
            for (TransferType type : paymentHistoryCriteria.getTypes()) {
                predicates.add(criteriaBuilder.equal(root.get("transfer_type"), type));
            }
        }
        if (paymentHistoryCriteria.getStatuses() != null) {
            for (Status status : paymentHistoryCriteria.getStatuses()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
