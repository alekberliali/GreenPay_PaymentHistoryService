package com.greentechpay.paymenthistoryservice.kafka.dto;

import com.greentechpay.paymenthistoryservice.dto.Currency;
import com.greentechpay.paymenthistoryservice.dto.Status;
import com.greentechpay.paymenthistoryservice.dto.TransferType;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class CreateBalanceToCard {
    private BigDecimal amount;
    private Timestamp paymentDate;
    private String transactionId;
    private Status status;
    private Long merchantId;
    private String userId;
    private String senderIban;
    private String requestField;
    private String senderRequestId;
    private Currency currency;
    private TransferType transferType;
}
