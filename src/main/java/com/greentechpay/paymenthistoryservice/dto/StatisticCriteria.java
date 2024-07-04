package com.greentechpay.paymenthistoryservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StatisticCriteria {
    List<Integer> serviceIdList;
    String userId;
    String iban;
    Currency currency;
    Integer vendorId;
    String categoryName;
    LocalDate startDate;
    LocalDate endDate;
}
