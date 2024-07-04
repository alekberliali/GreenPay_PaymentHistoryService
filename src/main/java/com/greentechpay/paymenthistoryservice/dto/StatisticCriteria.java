package com.greentechpay.paymenthistoryservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StatisticCriteria {
    List<Integer> serviceIdList;
    String iban;
    Integer vendorId;
    String categoryName;
    LocalDate startDate;
    LocalDate endDate;
}
