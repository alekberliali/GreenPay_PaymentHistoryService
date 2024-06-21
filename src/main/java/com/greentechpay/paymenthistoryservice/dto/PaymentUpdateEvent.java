package com.greentechpay.paymenthistoryservice.dto;

import lombok.Data;

@Data
public class PaymentUpdateEvent {
    private String correlationId;
    private String transactionId;
    private Status status;

}
