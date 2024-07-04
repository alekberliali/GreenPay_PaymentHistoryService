package com.greentechpay.paymenthistoryservice.service.specification;

import com.greentechpay.paymenthistoryservice.dto.StatisticCriteria;
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
public class StatisticSpecification implements Specification<PaymentHistory> {
    private final StatisticCriteria statisticCriteria;


    @Override
    public Predicate toPredicate(Root<PaymentHistory> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (statisticCriteria.getIban() != null) {
            predicates.add(criteriaBuilder.equal(root.get("senderIban"), statisticCriteria.getIban()));
        }
        if (statisticCriteria.getCategoryName() != null) {
            predicates.add(criteriaBuilder.equal(root.get("categoryName"), statisticCriteria.getCategoryName()));
        }
        if (statisticCriteria.getVendorId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("vendorId"), statisticCriteria.getVendorId()));
        }
        if (statisticCriteria.getServiceIdList()!=null){
            predicates.add(root.get("serviceId").in(statisticCriteria.getServiceIdList()));
        }
        if (statisticCriteria.getStartDate() != null && statisticCriteria.getEndDate() != null) {
            predicates.add(criteriaBuilder.between(root.get("date"), statisticCriteria.getStartDate(),
                    statisticCriteria.getEndDate()));
        } else if (statisticCriteria.getStartDate() == null && statisticCriteria.getEndDate() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), statisticCriteria.getEndDate()));
        } else if (statisticCriteria.getStartDate() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), statisticCriteria.getStartDate()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
