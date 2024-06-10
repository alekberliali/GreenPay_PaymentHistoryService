package com.greentechpay.paymenthistoryservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TResponse {
    private BigDecimal amount;
    private String transactionId;
    private Timestamp paymentDate;
    private String merchantId;
    private Timestamp updatedDate;
    private Status status;
    private String externalPaymentId;
    private String senderRequestId;
    private Currency currency;
    private String currencyOut;
    private String amountOut;
    private String userId;
    private Integer vendorId;
    private String toUser;
    private String requestField;
    private TransferType transferType;
    private Integer serviceId;
    private String categoryName;
}
