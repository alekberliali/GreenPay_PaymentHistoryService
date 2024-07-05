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
        if (paymentHistoryCriteria != null) {
            if (paymentHistoryCriteria.getUserId() != null) {
                var senderPredicate = criteriaBuilder.equal(root.get("userId"), paymentHistoryCriteria.getUserId());
                var receiverPredicate = criteriaBuilder.equal(root.get("toUser"), paymentHistoryCriteria.getUserId());
                predicates.add(criteriaBuilder.or(senderPredicate, receiverPredicate));
            }
            if (paymentHistoryCriteria.getSenderIban() != null) {
                var senderIbanPredicate = criteriaBuilder.equal(root.get("senderIban"), paymentHistoryCriteria.getSenderIban());
                var receiverIbanPredicate = criteriaBuilder.equal(root.get("receiverIban"), paymentHistoryCriteria.getSenderIban());
                predicates.add(criteriaBuilder.or(senderIbanPredicate, receiverIbanPredicate));
            }
            if (paymentHistoryCriteria.getTransactionId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("transactionId"), paymentHistoryCriteria.getTransactionId()));
            }
            if (paymentHistoryCriteria.getVendorId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("vendorId"), paymentHistoryCriteria.getVendorId()));
            }
            if (paymentHistoryCriteria.getMerchantId()!=null){
                predicates.add(criteriaBuilder.equal(root.get("merchantId"), paymentHistoryCriteria.getMerchantId()));
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
            if (paymentHistoryCriteria.getTransferTypes() != null) {
                predicates.add(root.get("transferType").in(paymentHistoryCriteria.getTransferTypes()));
            }
            if (paymentHistoryCriteria.getStatuses() != null) {
                predicates.add(root.get("status").in(paymentHistoryCriteria.getStatuses()));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
