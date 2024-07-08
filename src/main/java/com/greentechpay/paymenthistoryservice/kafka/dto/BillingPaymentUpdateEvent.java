package com.greentechpay.paymenthistoryservice.kafka.dto;

import lombok.Data;

@Data
public class BillingPaymentUpdateEvent<T> {
    private String correlationId;
    private String transactionId;
    private T operationContext;
}
