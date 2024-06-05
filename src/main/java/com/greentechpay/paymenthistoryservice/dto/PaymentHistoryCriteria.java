package com.greentechpay.paymenthistoryservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PaymentHistoryCriteria {
    private String userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String transactionId;
    private List<Currency> currencies;
    private List<TransferType> transferType;
    private List<Status> statuses;
}
