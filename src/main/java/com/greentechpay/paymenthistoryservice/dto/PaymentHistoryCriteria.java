package com.greentechpay.paymenthistoryservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PaymentHistoryCriteria {
    private Long id;
    private String userId;
    private String receiptId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String transactionId;
    private Integer vendorId;
    private String requestField;
    private String senderIban;
    private Long merchantId;
    private String categoryName;
    private List<Integer> serviceIdList;
    private List<Currency> currencies;
    private List<TransferType> transferTypes;
    private List<Status> statuses;
}
