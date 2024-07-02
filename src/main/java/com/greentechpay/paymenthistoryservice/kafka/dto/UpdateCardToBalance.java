package com.greentechpay.paymenthistoryservice.kafka.dto;

import com.greentechpay.paymenthistoryservice.dto.Currency;
import com.greentechpay.paymenthistoryservice.dto.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Data
public class UpdateCardToBalance {
    private String transactionId;
    private BigDecimal amount;
    private Status status;
    private Timestamp updateDate;
    private String externalPaymentId;
    private Currency currency;
    private String requestField;
}
