package com.greentechpay.paymenthistoryservice.kafka.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.greentechpay.paymenthistoryservice.dto.Currency;
import com.greentechpay.paymenthistoryservice.dto.Status;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentUpdateEvent {
    private String transactionId;
    private Status status;
    private Currency currencyOut;
    private String receiverIban;
    private BigDecimal amountOut;
}
