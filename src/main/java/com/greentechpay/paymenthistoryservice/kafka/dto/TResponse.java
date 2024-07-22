package com.greentechpay.paymenthistoryservice.kafka.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.greentechpay.paymenthistoryservice.dto.Currency;
import com.greentechpay.paymenthistoryservice.dto.Status;
import com.greentechpay.paymenthistoryservice.dto.TransferType;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;


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
    private BigDecimal amountOut;
    private String userId;
    private Integer vendorId;
    private String toUser;
    private String requestField;
    private TransferType transferType;
    private Integer serviceId;
}
