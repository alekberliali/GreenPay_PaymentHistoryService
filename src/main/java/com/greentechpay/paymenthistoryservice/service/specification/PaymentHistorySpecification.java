package com.greentechpay.paymenthistoryservice.service.specification;

import com.greentechpay.paymenthistoryservice.dto.PaymentHistoryCriteria;
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
        } else if (paymentHistoryCriteria.getStartDate() == null && paymentHistoryCriteria.getEndDate() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), paymentHistoryCriteria.getEndDate()));
        } else if (paymentHistoryCriteria.getStartDate() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), paymentHistoryCriteria.getStartDate()));
        }
        if (paymentHistoryCriteria.getCurrencies() != null) {
            predicates.add(root.get("currency").in(paymentHistoryCriteria.getCurrencies()));
        }
        if (paymentHistoryCriteria.getTransferType() != null) {
            predicates.add(root.get("transferType").in(paymentHistoryCriteria.getTransferType()));
        }
        if (paymentHistoryCriteria.getStatuses() != null) {
            predicates.add(root.get("status").in(paymentHistoryCriteria.getStatuses()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
