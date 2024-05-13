package com.greentechpay.paymenthistoryservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private Status status;
    private String externalPaymentId;
    private String senderRequestId;
    private Currency currency;
    private String currencyOut;
    private String amountOut;
    private String userId;
    private String toUser;
    private TransferType transferType;
    private String serviceName;
    private Integer categoryId;

}
