package com.greentechpay.paymenthistoryservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PaymentHistoryCriteria {
    private Long id;
    private String userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String transactionId;
    private Integer vendorId;
    private String requestField;
    private String senderIban;
    private Integer merchantId;
    private List<Currency> currencies;
    private List<TransferType> transferType;
    private List<Status> statuses;
}
