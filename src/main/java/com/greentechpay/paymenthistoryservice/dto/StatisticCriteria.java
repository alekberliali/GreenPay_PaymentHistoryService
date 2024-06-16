package com.greentechpay.paymenthistoryservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StatisticCriteria {
    String userId;
    LocalDate startDate;
    LocalDate endDate;
}
