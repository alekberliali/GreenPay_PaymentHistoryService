package com.greentechpay.paymenthistoryservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StatisticCriteria {
    String userId;
    Integer serviceId;
    LocalDate startDate;
    LocalDate endDate;
}
