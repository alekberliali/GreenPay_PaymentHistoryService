package com.greentechpay.paymenthistoryservice.kafka.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillingPaymentUpdateEvent<T> {
    private String correlationId;
    private String transactionId;
    private T operationContext;
}